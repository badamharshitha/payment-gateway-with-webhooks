package com.gateway.dto;

import com.gateway.entity.Payment;

import java.time.LocalDateTime;

public class PaymentResponse {

    private String id;
    private String orderId;
    private Integer amount;
    private String currency;
    private String method;
    private String vpa;
    private String status;
    private LocalDateTime createdAt;

    // ✅ STATIC MAPPER METHOD (THIS WAS MISSING)
    public static PaymentResponse from(Payment payment) {
        PaymentResponse r = new PaymentResponse();
        r.id = payment.getId();
        r.orderId = payment.getOrderId();
        r.amount = payment.getAmount();
        r.currency = payment.getCurrency();
        r.method = payment.getMethod();
        r.vpa = payment.getVpa();
        r.status = payment.getStatus();
        r.createdAt = payment.getCreatedAt();
        return r;
    }

    // Getters (needed for JSON serialization)
    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public Integer getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getMethod() { return method; }
    public String getVpa() { return vpa; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
