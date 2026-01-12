package com.gateway.controller;

import com.gateway.entity.Merchant;
import com.gateway.entity.Payment;
import com.gateway.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestBody Map<String, Object> requestBody,
            HttpServletRequest request
    ) {
        // Get authenticated merchant from request context
        Merchant merchant = (Merchant) request.getAttribute("merchant");

        // Basic validation
        if (!requestBody.containsKey("order_id") ||
            !requestBody.containsKey("method")) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid request body")
            );
        }

        // Create payment
        Payment payment = new Payment();
        payment.setId(generatePaymentId());
        payment.setMerchantId(merchant.getId());
        payment.setOrderId(requestBody.get("order_id").toString());
        payment.setMethod(requestBody.get("method").toString());
        payment.setStatus("pending");
        payment.setCurrency("INR");
        payment.setAmount(50000); // temp fixed amount

        if (requestBody.containsKey("vpa")) {
            payment.setVpa(requestBody.get("vpa").toString());
        }

        paymentRepository.save(payment);

        // Response
        Map<String, Object> response = new HashMap<>();
        response.put("id", payment.getId());
        response.put("order_id", payment.getOrderId());
        response.put("amount", payment.getAmount());
        response.put("currency", payment.getCurrency());
        response.put("method", payment.getMethod());
        response.put("status", payment.getStatus());
        response.put("created_at", payment.getCreatedAt());

        return ResponseEntity.status(201).body(response);
    }

    private String generatePaymentId() {
        return "pay_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}
