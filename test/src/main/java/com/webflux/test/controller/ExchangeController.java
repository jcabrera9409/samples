package com.webflux.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.test.service.ExchangeService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/from/{fromCurrency}/to/{toCurrency}")
    public Mono<ResponseEntity<Float>> getExchangeRate(@PathVariable String fromCurrency, @PathVariable String toCurrency) {
        log.info("From: {}, To: {}", fromCurrency, toCurrency);
        return exchangeService.getExchangeRate(fromCurrency, toCurrency)
                .map(rate -> ResponseEntity.ok(rate))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}