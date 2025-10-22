package com.webflux.test.service;

import reactor.core.publisher.Mono;

public interface IExchangeService {
    Mono<Float> getExchangeRate(String fromCurrency, String toCurrency);
}