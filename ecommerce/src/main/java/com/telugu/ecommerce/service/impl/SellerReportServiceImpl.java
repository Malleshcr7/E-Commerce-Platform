package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.SellerReport;
import com.telugu.ecommerce.repository.SellerReportRepository;
import com.telugu.ecommerce.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

    private final SellerReportRepository sellerReportRepository;


    @Override
    public SellerReport getSellerReport(Seller sellerId) {
        SellerReport sellerReport = sellerReportRepository.findBySellerId(sellerId.getId());

        if(sellerReport == null) {
            SellerReport sellerReport1 = new SellerReport();
            sellerReport1.setSeller(sellerId);
            return sellerReportRepository.save(sellerReport1);
        }
        return sellerReport;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
