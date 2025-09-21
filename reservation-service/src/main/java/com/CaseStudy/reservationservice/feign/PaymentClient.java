package com.CaseStudy.reservationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.CaseStudy.reservationservice.dto.Payment;


@FeignClient("PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping("/payment/create-payment-link")
    public String createPaymentLink(@RequestBody Payment payment);

    @GetMapping("/payment/callback")
    String paymentCallback(@RequestParam("razorpay_payment_id") String paymentId,
                           @RequestParam("razorpay_payment_link_id") String linkId,
                           @RequestParam("razorpay_payment_link_status") String status,
                           @RequestParam("razorpay_payment_link_reference_id") String referenceId);
}

