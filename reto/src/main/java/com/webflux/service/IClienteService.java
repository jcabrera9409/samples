package com.webflux.service;

import com.webflux.entidad.Cliente;

import reactor.core.publisher.Mono;

public interface IClienteService {
    Mono<Double> getCambio(Cliente cliente);
}
