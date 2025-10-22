package com.webflux.test.service;

import org.springframework.stereotype.Service;

import com.webflux.test.repository.ExchangeRepo;

import reactor.core.publisher.Mono;

@Service
public class ExchangeService implements IExchangeService {

    private final ExchangeRepo exchangeRepo;
    private final ExternalAPI externalAPI;

    public ExchangeService(ExchangeRepo exchangeRepo, ExternalAPI externalAPI) {
        this.exchangeRepo = exchangeRepo;
        this.externalAPI = externalAPI;
    }

    @Override
    public Mono<Float> getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .switchIfEmpty(Mono.defer(() -> {
                    return externalAPI.getExchangeRate(fromCurrency, toCurrency)
                        .flatMap(data -> exchangeRepo.save(data));
                }))
                .map(exchange -> exchange.getExchangeRate());
    }
}