package com.cart.cartServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cart.cartService.CartService;
import com.cart.client.UserProfileClient;
import com.cart.dto.AddToCartRequest;
import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.enumm.CartStatus;
import com.cart.enumm.CartType;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CartRepository;
import com.cart.response.MedicineResponse;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private UserProfileClient userProfileClient;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public void addToCart(AddToCartRequest req) {
		UsernamePasswordAuthenticationToken userAuthToken = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
		String userName = userAuthToken.getName();
		Optional<Cart>cartOpt = cartRepository.findByUserName(userName);
		Cart cart=null;
		MedicineResponse medicine =  userProfileClient.fetchMedicineData(req.getMedicineCode());
		CartItem cartItem = new CartItem(medicine);
		if(cartOpt.isEmpty()) {
			cart = new Cart();
			cart.setCartStatus(CartStatus.ACTIVE);
			cart.setCartType(CartType.DEFAULT);
			cart = cartRepository.save(cart);
		}else {
			cart = cartOpt.get();
		}
		cartItem.setCart(cart);
		cart.getItems().add(cartItem);
		cartRepository.save(cart);
	}

}
