package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Deal;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = dealService.getDeals();
        return ResponseEntity.ok(deals);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        Deal createdDeal = dealService.createDeal(deal);
        return new ResponseEntity<>(createdDeal, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal dealDetails) {
        dealDetails.setId(id);
        Deal updatedDeal = dealService.updateDeal(dealDetails);
        return ResponseEntity.ok(updatedDeal);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) {
        dealService.deleteDeal(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Deal deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
