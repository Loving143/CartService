package com.cart.cartServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.cartService.CartService;
import com.cart.client.UserProfileClient;
import com.cart.dto.AddToCartRequest;
import com.cart.entity.CartItem;
import com.cart.response.MedicineResponse;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private UserProfileClient userProfileClient;
	
	@Override
	public void addToCart(AddToCartRequest req) {
		MedicineResponse medicine =  userProfileClient.fetchMedicineData(req.getMedicineCode());
		CartItem cartItem = new CartItem(medicine);
		
		
	}

}
