package com.telugu.ecommerce.request;

import com.telugu.ecommerce.model.Address;
import com.telugu.ecommerce.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Cart cart;
    private Address shippingAddress;
}