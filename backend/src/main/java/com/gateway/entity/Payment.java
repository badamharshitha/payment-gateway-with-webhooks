package com.gateway.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private String id;

    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
        private String currency;
    
        @Column(nullable = false)
        private String method;
    
        private String vpa;
    
        @Column(nullable = false)
        private String status;
    
        private Boolean captured = false;
    
        @Column(name = "error_code")
        private String errorCode;
    
        @Column(name = "error_description")
        private String errorDescription;
        @Column(name = "created_at", nullable = false)
            private LocalDateTime createdAt;
        
            @PrePersist
            public void prePersist() {
                this.createdAt = LocalDateTime.now();
            }
        
            // ===== Getters & Setters =====
        
            public String getId() {
                return id;
            }
        
            public void setId(String id) {
                this.id = id;
            }
         public UUID getMerchantId() {
                return merchantId;
            }
        
            public void setMerchantId(UUID merchantId) {
                this.merchantId = merchantId;
            }
        
            public String getOrderId() {
                return orderId;
            }
        
            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }
        
            public Integer getAmount() {
                return amount;
            } public void setAmount(Integer amount) {
                    this.amount = amount;
                }
            
                public String getCurrency() {
                    return currency;
                }
            
                public void setCurrency(String currency) {
                    this.currency = currency;
                }
            
                public String getMethod() {
                    return method;
                }
            
                public void setMethod(String method) {
                    this.method = method;
                }
             public String getVpa() {
                    return vpa;
                }
            
                public void setVpa(String vpa) {
                    this.vpa = vpa;
                }
            
                public String getStatus() {
                    return status;
                }
            
                public void setStatus(String status) {
                    this.status = status;
                }
            
                public Boolean getCaptured() {
                    return captured;
                }
            public void setCaptured(Boolean captured) {
                    this.captured = captured;
                }
            
                public String getErrorCode() {
                    return errorCode;
                }
            
                public void setErrorCode(String errorCode) {
                    this.errorCode = errorCode;
                }
            
                public String getErrorDescription() {
                    return errorDescription;
                }
            
                public void setErrorDescription(String errorDescription) {
                    this.errorDescription = errorDescription;
                }
                    public LocalDateTime getCreatedAt() {
                        return createdAt;
                    }
                }
                