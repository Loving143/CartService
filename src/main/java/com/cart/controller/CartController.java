package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.cartService.CartService;
import com.cart.client.UserProfileClient;
import com.cart.dto.AddToCartRequest;
import com.cart.response.MedicineResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/addToCart")
	public ResponseEntity<?>addToCart(@RequestBody AddToCartRequest req){
		
		cartService.addToCart(req);
		return null;
	}

}
