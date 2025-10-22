package com.webflux.test.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.test.dto.ExternalAPIDTO;
import com.webflux.test.mapper.ExchangeMapper;
import com.webflux.test.model.Exchange;

import reactor.core.publisher.Mono;

@Service
public class ExternalAPI {

    private final String apiUrl = "https://v6.exchangerate-api.com/v6/1227cf5d13731ad6c251bc00/pair/";
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