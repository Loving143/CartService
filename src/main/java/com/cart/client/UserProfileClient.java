package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.response.MedicineResponse;

@FeignClient(name="userprofile-service" , url ="http://localhost:8044/userprofile")
public interface UserProfileClient {
	
	@GetMapping("/fetchMedicine/{medicineCode}")
	public MedicineResponse fetchMedicineData(@PathVariable String medicineCode); 

}
