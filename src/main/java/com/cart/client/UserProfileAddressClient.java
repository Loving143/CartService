package com.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cart.response.AddressResponse;

@FeignClient(name = "userprofileaddress-service", url = "http://localhost:8044")
public interface UserProfileAddressClient {

    @GetMapping("/address/fetchAddress/addressId/{addressId}")
    AddressResponse fetchAddressById(@PathVariable("addressId") Integer addressId);
}
