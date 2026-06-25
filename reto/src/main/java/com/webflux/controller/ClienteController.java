package com.webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.entidad.Cliente;
import com.webflux.service.IClienteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/cambio")
public class ClienteController {

    private IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping
    public Mono<ResponseEntity<Double>> getCambioPrestamo(@RequestBody Cliente cliente) {
        return clienteService.getCambio(cliente)
            .map(monto -> ResponseEntity.ok(monto))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
