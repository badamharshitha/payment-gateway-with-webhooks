package com.gateway.controller;

import com.gateway.dto.CreatePaymentRequest;
import com.gateway.dto.PaymentResponse;
import com.gateway.entity.Merchant;
import com.gateway.entity.Payment;
import com.gateway.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request,
            HttpServletRequest httpRequest
    ) {
        Merchant merchant = (Merchant) httpRequest.getAttribute("merchant");

        Payment payment = new Payment();
        payment.setId("pay_" + UUID.randomUUID().toString().replace("-", ""));
        payment.setMerchantId(merchant.getId());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setCurrency("INR");
        payment.setMethod(request.getMethod());
        payment.setVpa(request.getVpa());
        payment.setStatus("created");

        paymentRepository.save(payment);

        return ResponseEntity.ok(PaymentResponse.from(payment));
    }
}
