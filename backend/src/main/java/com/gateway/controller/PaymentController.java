package com.gateway.controller;

import com.gateway.dto.CreatePaymentRequest;
import com.gateway.dto.PaymentResponse;
import com.gateway.entity.Merchant;
import com.gateway.entity.Payment;
import com.gateway.repository.MerchantRepository;
import com.gateway.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final MerchantRepository merchantRepository;

    public PaymentController(
            PaymentRepository paymentRepository,
            MerchantRepository merchantRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.merchantRepository = merchantRepository;
    }

    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(
            @RequestBody CreatePaymentRequest request,
            HttpServletRequest httpRequest
    ) {

        /* =======================
           1. Validate request body
           ======================= */
        if (request == null ||
                request.getOrderId() == null ||
                request.getAmount() == null ||
                request.getMethod() == null ||
                request.getVpa() == null) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing required payment fields");
        }

        /* =======================
           2. Get merchant from filter
           ======================= */
        Object merchantObj = httpRequest.getAttribute("merchant");

        if (merchantObj == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Merchant authentication failed");
        }

        Merchant merchant = (Merchant) merchantObj;

        /* =======================
           3. Verify merchant exists
           ======================= */
        Optional<Merchant> dbMerchant =
                merchantRepository.findById(merchant.getId());

        if (dbMerchant.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Merchant not found");
        }

        /* =======================
           4. Create payment safely
           ======================= */
        Payment payment = new Payment();
        payment.setId("pay_" + UUID.randomUUID().toString().replace("-", ""));
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

        /* =======================
           5. Save to DB
           ======================= */
        Payment savedPayment = paymentRepository.save(payment);

        /* =======================
           6. Return response
           ======================= */
        return ResponseEntity.ok(
                PaymentResponse.from(savedPayment)
        );
    }
}
