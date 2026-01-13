package com.gateway.dto;

import lombok.Data;

@Data
public class CreatePaymentRequest {
    private String orderId;
    private String method;
    private String vpa;
}
