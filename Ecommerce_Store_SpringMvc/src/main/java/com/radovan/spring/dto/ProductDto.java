package com.radovan.spring.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ProductDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer productId;
	
	@NotEmpty(message = "Please select a category")
	private String productCategory;
	
	private String productDescription;
	
	@NotEmpty(message = "Please provide a manufacturer")
	private String productManufacturer;
	
	@NotEmpty(message = "Product Name is mandatory")
	private String productName;
	
	@NotNull(message="Please provide some price")
	@Min(value = 10, message = "Minimum value should be greater than 10")
	@Min(value = 0, message = "Enter valid number")
	private Double productPrice;
	
	@NotNull(message="Please provide unit stock")
	@Min(value = 0, message = "Enter valid number")
	private Integer unitStock;

	private String imageName;
	
	private List<String> categoryList;
	
	
	public ProductDto() {
		super();
		categoryList = new ArrayList<String>();
		categoryList.add("Android");
		categoryList.add("Mobile");
		categoryList.add("Mac");
		categoryList.add("Iphone");
		categoryList.add("Laptop");
		categoryList.add("TV");
		categoryList.add("Game Console");
		
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductManufacturer() {
		return productManufacturer;
	}

	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	

	public Integer getUnitStock() {
		return unitStock;
	}

	public void setUnitStock(Integer unitStock) {
		this.unitStock = unitStock;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	
	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	public String getMainImagePath() {
		if (productId == null || imageName == null)
			return "/images/productImages/unknown.jpg";
		return "/images/productImages/" + this.imageName;
	}

	@Override
	public String toString() {
		return "ProductDto [productId=" + productId + ", productCategory=" + productCategory + ", productDescription="
				+ productDescription + ", productManufacturer=" + productManufacturer + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", unitStock=" + unitStock + ", imageName=" + imageName + "]";
	}
	
	
}
