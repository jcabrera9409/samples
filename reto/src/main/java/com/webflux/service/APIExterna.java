package com.webflux.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.webflux.dto.APIExternaResponseDTO;
import com.webflux.entidad.Cliente;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class APIExterna {
    private String apiURL = "https://api.frankfurter.dev/v1/latest?base=";

    private final WebClient webClient;

    public APIExterna(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiURL).build();
    }

    public Mono<Cliente> getCambioCliente(Cliente cliente) {
        String fullUrl = this.apiURL.concat(cliente.getMonedaOrigen()).concat("&symbols=").concat(cliente.getMonedaDestino());
        log.info(fullUrl);
        return webClient.get()
            .uri(fullUrl)
            .retrieve()
            .bodyToMono(APIExternaResponseDTO.class)
            .map(response -> mapApiResponse(cliente, response));
    }

    private Cliente mapApiResponse(Cliente cliente, APIExternaResponseDTO response) {
        return Cliente.builder()
            .clienteId(cliente.getClienteId())
            .monto(cliente.getMonto() - (cliente.getMonto() * 0.05f))
            .tasa(response.rates().get(cliente.getMonedaDestino()))
            .monedaOrigen(cliente.getMonedaOrigen())
            .monedaDestino(cliente.getMonedaDestino())
            .build();
    }
}
