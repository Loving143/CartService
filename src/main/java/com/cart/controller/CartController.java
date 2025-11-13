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
import com.cart.cartService.OrderService;
import com.cart.dto.AddToCartRequest;
import com.cart.dto.OrderRequest;
import com.cart.entity.CartItem;
import com.cart.response.CartResponse;
import com.cart.response.OrderResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
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
    @DeleteMapping("/remove/cartItems/{itemId}")
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
    @PutMapping("/checkout")
    public String checkout() {
        return cartService.checkout();
    }
    
    @GetMapping("/size")
    public int getCartSize() {
        return cartService.getCartSizeForCurrentUser();
    }
    
    @GetMapping("/get/cartItems")
    public ResponseEntity<?> getCurrentUserCartItems(){
    	return  ResponseEntity.ok(cartService.getcurrentUserCartItems());
    }

    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<?> updateCartQuantity(@PathVariable Long id,
            @RequestParam String action) {

        CartItem updatedItem = cartService.updateCartQuantity(id, action);
        return ResponseEntity.ok(updatedItem);
    }
    
    @PostMapping("/create/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }
}
