package com.example.demo.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Reservation;
import com.example.demo.entity.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PaymentService {


    
    public String createPaymentLink(Payment payment) {
    	 
		String keyId = "rzp_test_78CNUyFbUgi1IZ";
		String keySecret = "zvbQoG99P9S3R2kRDPz6SXRD";
		String callbackUrl = "http://localhost:8087/payment/callback";
 
		try {
 
			RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
			
			String username = payment.getUsername();
			double amount=payment.getAmount();
			String referenceId = (username + "_" + amount + "_" + UUID.randomUUID()).substring(0, 40);
			System.out.println(username + "_" + amount + "_" + UUID.randomUUID().toString());
 
			JSONObject paymentLinkRequest = new JSONObject();
 
			paymentLinkRequest.put("amount", amount * 100);
 
			paymentLinkRequest.put("currency", "INR");
 
			paymentLinkRequest.put("expire_by", (System.currentTimeMillis() / 1000) + 1000); // 5 min link valid
 
			paymentLinkRequest.put("callback_url", callbackUrl);
 
			paymentLinkRequest.put("callback_method", "get");
 
			paymentLinkRequest.put("reference_id", referenceId);
			System.out.println(referenceId);
			PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
 
			String url = paymentLink.get("short_url"); // payment link
			return url;
 
		} catch (RazorpayException e) {
 
			e.printStackTrace();
 
			return null;
 
		}
	}

   
}

