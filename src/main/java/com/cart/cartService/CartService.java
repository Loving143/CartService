package com.cart.cartService;

import java.util.List;

import com.cart.dto.AddToCartRequest;
import com.cart.entity.CartItem;
import com.cart.response.CartItemResponse;
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

	List<CartItemResponse> getcurrentUserCartItems();

	CartItem updateCartQuantity(Long id, String action);

}
