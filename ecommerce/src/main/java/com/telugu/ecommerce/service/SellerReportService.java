package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller sellerId);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
