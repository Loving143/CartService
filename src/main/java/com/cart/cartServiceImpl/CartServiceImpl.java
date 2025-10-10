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
import com.cart.mapper.CartMapper;
import com.cart.repository.CartItemRepository;
import com.cart.repository.CartRepository;
import com.cart.response.CartResponse;
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
	
	 @Override
	    public CartResponse getCartForCurrentUser() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("No active cart found for user"));
	        return CartMapper.toResponse(cart);
	    }

	    @Override
	    public void updateCartItemQuantity(Long itemId, int quantity) {
	        CartItem item = cartItemRepository.findById(itemId)
	                .orElseThrow(() -> new RuntimeException("Item not found"));
	        item.setQuantity(quantity);
	        cartItemRepository.save(item);
	    }

	    @Override
	    public void removeCartItem(Long itemId) {
	        cartItemRepository.deleteById(itemId);
	    }

	    @Override
	    public void clearCart() {
	        String userName = getCurrentUser();
	        cartRepository.findByUserName(userName)
	                .ifPresent(cart -> {
	                    cart.getItems().clear();
	                    cartRepository.save(cart);
	                });
	    }

	    @Override
	    public double calculateTotal() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("No active cart found"));
	        return cart.getItems().stream()
	                .mapToDouble(i -> i.getPrice() * i.getQuantity())
	                .sum();
	    }

	    @Override
	    public String checkout() {
	        String userName = getCurrentUser();
	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("Cart not found"));
	        cart.setCartStatus(CartStatus.CHECKED_OUT);
	        cartRepository.save(cart);
	        return "Checkout successful for user: " + userName;
	    }

	    private String getCurrentUser() {
	        UsernamePasswordAuthenticationToken userAuthToken =
	                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
	        return userAuthToken.getName();
	    }
	    
	    @Override
	    public int getCartSizeForCurrentUser() {
	        String userName = getCurrentUser();

	        Cart cart = cartRepository.findByUserName(userName)
	                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

	        // Number of distinct items in cart
	        return cart.getItems().size();
	    }

}
