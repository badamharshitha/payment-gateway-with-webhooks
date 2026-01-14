package com.gateway.dto;

public class CreatePaymentRequest {

    private String orderId;
    private Integer amount;
    private String method;
    private String vpa;

    public String getOrderId() { return orderId; }
    public Integer getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getVpa() { return vpa; }
}