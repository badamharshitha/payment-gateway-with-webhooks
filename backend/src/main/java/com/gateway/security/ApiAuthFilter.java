package com.gateway.security;

import com.gateway.entity.Merchant;
import com.gateway.repository.MerchantRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class ApiAuthFilter extends OncePerRequestFilter {

    private final MerchantRepository merchantRepository;

    public ApiAuthFilter(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Allow non-API routes
        if (!path.startsWith("/api/v1")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("X-Api-Key");
        String apiSecret = request.getHeader("X-Api-Secret");

        if (apiKey == null || apiSecret == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing API credentials");
            return;
        }

        Optional<Merchant> merchant =
                merchantRepository.findByApiKeyAndApiSecret(apiKey, apiSecret);

        if (merchant.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid API credentials");
            return;
        }

        // Attach merchant to request context
        request.setAttribute("merchant", merchant.get());

        // Continue request
        filterChain.doFilter(request, response);
    }
}
