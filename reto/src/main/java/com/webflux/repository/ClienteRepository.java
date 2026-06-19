package com.webflux.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.webflux.entidad.Cliente;

import reactor.core.publisher.Mono;

@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Long>{
    public Mono<Cliente> findByClienteId(String clienteId);
}
