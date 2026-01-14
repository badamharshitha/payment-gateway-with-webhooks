package com.gateway.controller;

import com.gateway.dto.CreatePaymentRequest;
import com.gateway.dto.PaymentResponse;
import com.gateway.entity.Merchant;
import com.gateway.entity.Payment;
import com.gateway.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

        // 🔴 ROOT CAUSE FIX: merchant attribute may be null → causing 500
        Object merchantObj = httpRequest.getAttribute("merchant");
        if (merchantObj == null) {
            throw new RuntimeException("Merchant not authenticated");
        }

        Merchant merchant = (Merchant) merchantObj;

        Payment payment = new Payment();
        payment.setId("pay_" + UUID.randomUUID());
        payment.setMerchantId(merchant.getId());
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setCurrency("INR");
        payment.setMethod(request.getMethod());
        payment.setVpa(request.getVpa());
        payment.setStatus("created");
        payment.setCaptured(false);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return ResponseEntity.ok(PaymentResponse.from(payment));
    }
}
