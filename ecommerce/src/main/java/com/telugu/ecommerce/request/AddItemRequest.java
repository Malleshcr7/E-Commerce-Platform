package com.telugu.ecommerce.request;

import com.telugu.ecommerce.model.Product;
import lombok.Data;

@Data
public class AddItemRequest {
    private Long productId;
    private int size;
    private int quantity;
}
