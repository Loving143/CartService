package com.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cart.entity.Cart;
import com.cart.entity.CartItem;

public interface CartRepository extends JpaRepository<Cart,Long>{

	Optional<Cart> findByUserName(String userName);

	@Query("Select cartItems"
			+ " from Cart cart "
			+ " inner join cart.items as cartItems "
			+ " where cart.userName =:userName")
	List<CartItem> fetchCurrentUsersCartItems(String userName);

}
