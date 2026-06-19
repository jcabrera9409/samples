package com.webflux.service;

import org.springframework.stereotype.Service;

import com.webflux.entidad.Cliente;
import com.webflux.repository.ClienteRepository;

import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final APIExterna apiExterna;

    public ClienteServiceImpl(ClienteRepository clienteRepository, APIExterna apiExterna) {
        this.clienteRepository = clienteRepository;
        this.apiExterna = apiExterna;
    }

    @Override
    public Mono<Double> getCambio(Cliente cliente) {
        return clienteRepository.findByClienteId(cliente.getClienteId())
            .switchIfEmpty(Mono.defer(() -> {
                return apiExterna.getCambioCliente(cliente)
                    .flatMap(data -> clienteRepository.save(data));
                })
            )
            .map(db -> db.getMonto());
    }
    
}
