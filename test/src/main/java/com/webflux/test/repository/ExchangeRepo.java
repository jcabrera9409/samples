package com.webflux.test.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.webflux.test.model.Exchange;

import reactor.core.publisher.Mono;

@Repository
public interface ExchangeRepo extends R2dbcRepository<Exchange, Long> {
    public Mono<Exchange> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}