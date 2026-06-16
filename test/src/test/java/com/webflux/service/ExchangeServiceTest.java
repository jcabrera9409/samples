package com.webflux.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.webflux.model.Exchange;
import com.webflux.repository.ExchangeRepo;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias - ExchangeService")
class ExchangeServiceTest {

    @Mock
    private ExchangeRepo exchangeRepo;

    @Mock
    private ExternalAPI externalAPI;

    @InjectMocks
    private ExchangeService exchangeService;

    private Exchange exchangeMock;
    private String fromCurrency;
    private String toCurrency;
    private Float exchangeRate;

    @BeforeEach
    void configurarPruebaBase() {
        fromCurrency = "USD";
        toCurrency = "EUR";
        exchangeRate = 0.85f;
        
        exchangeMock = Exchange.builder()
                .id(1L)
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .exchangeRate(exchangeRate)
                .build();
    }

    @Test
    @DisplayName("Debería retornar el tipo de cambio desde la base de datos cuando existe")
    void deberiaRetornarTipoDeCambioDesdeBD_CuandoExiste() {
        // Arrange - Preparar
        when(exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency))
                .thenReturn(Mono.just(exchangeMock));

        // Act - Actuar
        Mono<Float> resultado = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        // Assert - Verificar
        StepVerifier.create(resultado)
                .expectNext(exchangeRate)
                .verifyComplete();

        verify(exchangeRepo, times(1)).findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        verify(externalAPI, never()).getExchangeRate(anyString(), anyString());
        verify(exchangeRepo, never()).save(any(Exchange.class));
    }

    @Test
    @DisplayName("Debería consultar API externa y guardar cuando no existe en BD")
    void deberiaConsultarAPIExternaYGuardar_CuandoNoExisteEnBD() {
        // Arrange - Preparar
        when(exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency))
                .thenReturn(Mono.empty());
        when(externalAPI.getExchangeRate(fromCurrency, toCurrency))
                .thenReturn(Mono.just(exchangeMock));
        when(exchangeRepo.save(any(Exchange.class)))
                .thenReturn(Mono.just(exchangeMock));

        // Act - Actuar
        Mono<Float> resultado = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        // Assert - Verificar
        StepVerifier.create(resultado)
                .expectNext(exchangeRate)
                .verifyComplete();

        verify(exchangeRepo, times(1)).findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        verify(externalAPI, times(1)).getExchangeRate(fromCurrency, toCurrency);
        verify(exchangeRepo, times(1)).save(any(Exchange.class));
    }

    @Test
    @DisplayName("Debería propagar error cuando la API externa falla")
    void deberiaPropgarError_CuandoAPIExternaFalla() {
        // Arrange - Preparar
        String mensajeError = "Error al conectar con la API externa";
        when(exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency))
                .thenReturn(Mono.empty());
        when(externalAPI.getExchangeRate(fromCurrency, toCurrency))
                .thenReturn(Mono.error(new RuntimeException(mensajeError)));

        // Act - Actuar
        Mono<Float> resultado = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        // Assert - Verificar
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals(mensajeError))
                .verify();

        verify(exchangeRepo, times(1)).findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        verify(externalAPI, times(1)).getExchangeRate(fromCurrency, toCurrency);
        verify(exchangeRepo, never()).save(any(Exchange.class));
    }

    @Test
    @DisplayName("Debería propagar error cuando la búsqueda en BD falla")
    void deberiaPropgarError_CuandoBusquedaEnBDFalla() {
        // Arrange - Preparar
        String mensajeError = "Error al consultar la base de datos";
        when(exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency))
                .thenReturn(Mono.error(new RuntimeException(mensajeError)));

        // Act - Actuar
        Mono<Float> resultado = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        // Assert - Verificar
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals(mensajeError))
                .verify();

        verify(exchangeRepo, times(1)).findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        verify(externalAPI, never()).getExchangeRate(anyString(), anyString());
        verify(exchangeRepo, never()).save(any(Exchange.class));
    }

    @Test
    @DisplayName("Debería consultar API y guardar exitosamente cuando el guardado falla")
    void deberiaConsultarAPIYPropgarError_CuandoGuardadoFalla() {
        // Arrange - Preparar
        String mensajeError = "Error al guardar en la base de datos";
        when(exchangeRepo.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency))
                .thenReturn(Mono.empty());
        when(externalAPI.getExchangeRate(fromCurrency, toCurrency))
                .thenReturn(Mono.just(exchangeMock));
        when(exchangeRepo.save(any(Exchange.class)))
                .thenReturn(Mono.error(new RuntimeException(mensajeError)));

        // Act - Actuar
        Mono<Float> resultado = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        // Assert - Verificar
        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals(mensajeError))
                .verify();

        verify(exchangeRepo, times(1)).findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        verify(externalAPI, times(1)).getExchangeRate(fromCurrency, toCurrency);
        verify(exchangeRepo, times(1)).save(any(Exchange.class));
    }
}
