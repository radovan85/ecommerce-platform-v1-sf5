package com.radovan.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.radovan.spring.entity.CartItemEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {


	@Query(value = "select distinct c.* " + 
	        "from cart_items as c " + 
	        "inner join products as p on c.product_id= p.id " + 
	        "where  c.cart_id = :cartId", nativeQuery = true) 
	List<CartItemEntity> findAllByCartId(@Param ("cartId") Integer cartId);
	
	@Query(value = "select * from cart_items where product_id = :productId",nativeQuery = true)
	List<CartItemEntity> findAllByProductId(@Param ("productId") Integer productId);
	
	@Modifying
	@Query(value="delete from cart_items where cart_id = :cartId",nativeQuery=true)
	void removeAllByCartId(@Param ("cartId") Integer cartId);
	
	@Modifying
	@Query(value="delete from cart_items where product_id = :productId",nativeQuery=true)
	void removeAllByProductId(@Param ("productId") Integer productId);
	
	@Modifying
	@Query(value = "delete from cart_items where id = :itemId",nativeQuery = true)
	void removeCartItem(@Param ("itemId") Integer itemId);

	@Query(value="select sum(price) from cart_items where cart_id = :cartId",nativeQuery = true)
	Double calculateGrandTotal(@Param ("cartId") Integer cartId);
}



