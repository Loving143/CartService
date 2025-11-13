package com.cart.helper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	 @Override
	    public void commence(HttpServletRequest request, HttpServletResponse response,
	                        AuthenticationException authException) throws IOException {
	        
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("status", HttpStatus.UNAUTHORIZED.value());
	        body.put("error", "Unauthorized");
	        body.put("message", authException.getMessage());
	        body.put("path", request.getRequestURI());
	        
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(response.getOutputStream(), body);
}
}
