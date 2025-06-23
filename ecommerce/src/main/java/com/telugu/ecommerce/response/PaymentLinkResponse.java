package com.telugu.ecommerce.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String payment_Link_url;
    private String payment_Link_id;
}
