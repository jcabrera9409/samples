package org.quarkus.service;

import org.quarkus.repository.ExchangeRepo;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExchangeService {

    private final ExchangeRepo exchangeRepo;
    
    public ExchangeService(ExchangeRepo exchangeRepo) {
        this.exchangeRepo = exchangeRepo;
    }
    
    public Uni<Float> getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRepo.findByFromAndToCurrency(fromCurrency, toCurrency)
                .onItem()
                .ifNotNull()
                .transform(exchange -> exchange.getExchangeRate());
    }
}