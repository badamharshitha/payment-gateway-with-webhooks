package com.gateway.config;

import com.gateway.entity.Merchant;
import com.gateway.repository.MerchantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initMerchants(MerchantRepository merchantRepository) {
        return args -> {
            boolean exists = merchantRepository
                    .findByApiKeyAndApiSecret("key_test_abc123", "secret_test_xyz789")
                    .isPresent();

            if (!exists) {
                Merchant merchant = new Merchant();
                merchant.setId(UUID.randomUUID());
                merchant.setEmail("test@example.com");
                merchant.setApiKey("key_test_abc123");
                merchant.setApiSecret("secret_test_xyz789");
                merchant.setWebhookSecret("whsec_test_abc123");

                merchantRepository.save(merchant);

                System.out.println("✅ Test merchant seeded");
            }
        };
    }
}
