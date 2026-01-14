package com.gateway.service;

import com.gateway.entity.WebhookEvent;
import com.gateway.repository.WebhookEventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WebhookService {

    private final WebhookEventRepository repo;

    public WebhookService(WebhookEventRepository repo) {
        this.repo = repo;
    }

    public void recordEvent(UUID merchantId, String eventType, String payload) {
        WebhookEvent e = new WebhookEvent();
        e.setId("wh_" + System.currentTimeMillis());
        e.setMerchantId(merchantId);
        e.setEventType(eventType);
        e.setPayload(payload);
        repo.save(e);
    }}