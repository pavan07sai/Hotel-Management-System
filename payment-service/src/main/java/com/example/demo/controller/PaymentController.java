package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.PaymentService;
import com.example.demo.dto.Reservation;
import com.example.demo.entity.Payment;
import com.example.demo.feign.ReservationClient;
import com.example.demo.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ReservationClient reservationClient;

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/create-payment-link")
    public String createPaymentLink(@RequestBody Payment payment) {
        String paymentLink = paymentService.createPaymentLink(payment);
        return paymentLink;
    }

    @GetMapping("/callback")
    public String paymentCallback(@RequestParam String razorpay_payment_id,
                                  @RequestParam String razorpay_payment_link_id,
                                  @RequestParam String razorpay_payment_link_status,
                                  @RequestParam String razorpay_payment_link_reference_id) {

        if (!"paid".equals(razorpay_payment_link_status)) {
            return "Payment failed";
        }

        try {
            // Assuming reference ID is in format: reservation_123
            String[] referenceParts = razorpay_payment_link_reference_id.split("_");
            if (referenceParts.length < 2) {
                return "Invalid reference ID format";
            }

            Long reservationId = Long.parseLong(referenceParts[1]);

            // Fetch reservation details using Feign client
            Reservation reservation = reservationClient.getReservationById(reservationId);

            if (reservation == null) {
                return "Reservation not found";
            }

            // Save payment with actual reservation amount
            Payment payment = new Payment();
            payment.setUsername(razorpay_payment_link_reference_id);
            payment.setStatus(razorpay_payment_link_status);
            payment.setAmount(payment.getAmount()); // Assuming getAmount() returns a double or int
            paymentRepository.save(payment);

            String html = "<h2>Payment successful for reservation</h2>"
                    + "<a href='http://localhost:5173/login' "
                    + "style='display:inline-block;margin-top:20px;padding:10px 20px;background:#bfa16b;color:#fff;text-decoration:none;border-radius:5px;'>"
                    + "Go to Manager Dashboard</a>";
            return html;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing payment callback";
        }
    }
}
