package com.cart.cartService;

import com.cart.dto.AddToCartRequest;
import com.cart.response.CartResponse;

public interface CartService {

	void addToCart(AddToCartRequest req);

	CartResponse getCartForCurrentUser();

	void updateCartItemQuantity(Long itemId, int quantity);

	void removeCartItem(Long itemId);

	void clearCart();

	double calculateTotal();

	String checkout();

	int getCartSizeForCurrentUser();

}
