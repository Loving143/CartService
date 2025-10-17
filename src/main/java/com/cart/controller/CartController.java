package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cart.cartService.CartService;
import com.cart.dto.AddToCartRequest;
import com.cart.response.CartResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/addToCart")
	public ResponseEntity<?>addToCart(@RequestBody AddToCartRequest req){
		cartService.addToCart(req);
		return ResponseEntity.ok("Cart added successfully!!");
	}
	
	// ✅ Get current cart
    @GetMapping
    public CartResponse getCart() {
        return cartService.getCartForCurrentUser();
    }

    // ✅ Update item quantity
    @PutMapping("/item/{itemId}")
    public void updateQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(itemId, quantity);
    }

    // ✅ Remove single item
    @DeleteMapping("/item/{itemId}")
    public void removeItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
    }

    // ✅ Clear cart
    @DeleteMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }

    // ✅ Get total price
    @GetMapping("/total")
    public double getCartTotal() {
        return cartService.calculateTotal();
    }

    // ✅ Checkout (optional)
    @PostMapping("/checkout")
    public String checkout() {
        return cartService.checkout();
    }
    
    @GetMapping("/size")
    public int getCartSize() {
        return cartService.getCartSizeForCurrentUser();
    }

}
