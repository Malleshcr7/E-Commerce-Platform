package com.telugu.ecommerce.controller;

import com.razorpay.RazorpayException;
import com.telugu.ecommerce.model.*;
import com.telugu.ecommerce.repository.UserRepository;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final UserService userService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;


    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSucessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt) throws RazorpayException {

        User user = userService.findUserByJwtToken(jwt);

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.ProceedPayment(paymentOrder, paymentId, paymentLinkId);

        if (paymentSuccess) {
            for (Order order : paymentOrder.getOrders()) {
                transactionService.crateTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings(report.getTotalEarnings() + order.getTotalsellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getTotalsellingPrice());
                sellerReportService.updateSellerReport(report);
            }
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment Successfull");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
