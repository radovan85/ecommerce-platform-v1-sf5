package com.radovan.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.ProductService;
import com.radovan.spring.service.UserService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartItemService cartItemService;

	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public String listAllProducts(ModelMap map, Principal principal) {
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		try {
			CustomerDto customer = customerService.getCustomerByUserId(authUser.getId());
			Integer cartId = customer.getCartId();
			map.put("cartId", cartId);
		} catch (Exception ex) {
			// will activate if role is Admin
		}

		List<ProductDto> products = productService.listAll();
		map.put("products", products);
		return "productList";
	}

	@RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
	public String getProductById(@PathVariable("productId") Integer productId, ModelMap map, Principal principal) {
		UserEntity authUser = userService.getUserByEmail(principal.getName());
		try {
			CustomerDto customer = customerService.getCustomerByUserId(authUser.getId());
			Integer cartId = customer.getCartId();
			map.put("cartId", cartId);
		} catch (Exception ex) {
			// will activate if role is Admin
		}

		ProductDto product = productService.getProduct(productId);
		map.put("productObj", product);
		map.put("tempProductId", product.getProductId());
		return "productPage";
	}

	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.GET)
	public String getProductForm(ModelMap map) {
		ProductDto product = new ProductDto();
		map.put("productFormObj", product);
		return "addProduct";
	}

	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)
	public String addProductPost(@Validated @ModelAttribute("productFormObj") ProductDto product, Errors errors,
			ModelMap map, @RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName,
			RedirectAttributes redirectAttributes) throws IOException {

		if (errors.hasErrors()) {
			return "addProduct";
		}

		System.out.println(product.toString());

		String fileLocation = "C:\\Users\\pc\\workspace2\\Ecommerce_Store_SpringMvc-main\\Ecommerce_Store_SpringMvc\\src\\main\\resources\\static\\images\\productImages\\";
		String imageUUID;

		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(fileLocation, imageUUID);

		if (file != null && !file.isEmpty()) {
			Files.write(fileNameAndPath, file.getBytes());
			System.out.println("IMage Save at:" + fileNameAndPath.toString());
		} else {
			imageUUID = imgName;
		}

		product.setImageName(imageUUID);
		productService.addProduct(product);

		return "redirect:/getAllProducts";
	}

	@RequestMapping(value = "/admin/product/updateProduct/{productId}", method = RequestMethod.GET)
	public String updateProduct(@PathVariable("productId") Integer productId, ModelMap map) throws IOException {

		ProductDto product = new ProductDto();
		ProductDto currentProduct = productService.getProduct(productId);
		map.put("productFormObj", product);
		map.put("currentProduct", currentProduct);
		return "updateProduct";
	}

	@RequestMapping(value = "/admin/product/updateProduct/{productId}", method = RequestMethod.POST)
	public String updateProduct(@Validated @ModelAttribute("productFormObj") ProductDto product, Errors errors,
			@PathVariable("productId") Integer productId, ModelMap map,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName,
			RedirectAttributes redirectAttributes) throws IOException {

		ProductDto currentProduct = productService.getProduct(productId);

		if (errors.hasErrors()) {
			System.out.println("Errors are detected");
			map.put("selectedId", productId);
			map.put("currentProduct", currentProduct);
			return "updateProduct";
		}

		String fileLocation = "C:\\Users\\pc\\workspace2\\Ecommerce_Store_SpringMvc-main\\Ecommerce_Store_SpringMvc\\src\\main\\resources\\static\\images\\productImages\\";
		String imageUUID;

		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(fileLocation, imageUUID);

		if (file != null && !file.isEmpty()) {
			Files.write(fileNameAndPath, file.getBytes());
			System.out.println("IMage Save at:" + fileNameAndPath.toString());
		} else {
			imageUUID = imgName;
		}

		product.setImageName(imageUUID);
		productService.updateProduct(productId, product);

		return "redirect:/getAllProducts";

	}

	@RequestMapping(value = "/admin/delete/{productId}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable(value = "productId") int productId) {

		ProductDto product = productService.getProduct(productId);
		Path path = Paths.get(
				"C:\\Users\\pc\\workspace2\\Ecommerce_Store_SpringMvc-main\\Ecommerce_Store_SpringMvc\\src\\main\\resources\\static\\images\\productImages\\"
						+ product.getImageName());

		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		cartItemService.eraseAllByProductId(productId);
		productService.deleteProduct(product.getProductId());
		return "redirect:/getAllProducts";
	}
}