package com.webflux.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.dto.ExternalAPIDTO;
import com.webflux.mapper.ExchangeMapper;
import com.webflux.model.Exchange;

import reactor.core.publisher.Mono;

@Service
public class ExternalAPI {

    @Value("${application.external-api.url}")
    private String apiUrl;
    private final WebClient webClient;

    public ExternalAPI(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public Mono<Exchange> getExchangeRate(String fromCurrency, String toCurrency) {
        String url = apiUrl + fromCurrency + "/" + toCurrency;
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(ExternalAPIDTO.class)
                .map(ExchangeMapper::toModel);
    }
}