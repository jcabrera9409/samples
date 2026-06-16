package com.webflux.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.webflux.dto.ExternalAPIDTO;
import com.webflux.model.Exchange;

@DisplayName("Pruebas Unitarias - ExchangeMapper")
class ExchangeMapperTest {

    private ExternalAPIDTO externalAPIDTO;
    private String fromCurrency;
    private String toCurrency;
    private Float exchangeRate;

    @BeforeEach
    void configurarPruebaBase() {
        fromCurrency = "USD";
        toCurrency = "EUR";
        exchangeRate = 0.85f;
        
        externalAPIDTO = new ExternalAPIDTO(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                fromCurrency,
                toCurrency,
                exchangeRate
        );
    }

    @Test
    @DisplayName("Debería mapear correctamente ExternalAPIDTO a Exchange")
    void deberiaMapearCorrectamente_ExternalAPIDTO_AExchange() {
        // Arrange - Preparar (ya configurado en @BeforeEach)

        // Act - Actuar
        Exchange resultado = ExchangeMapper.toModel(externalAPIDTO);

        // Assert - Verificar
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(fromCurrency, resultado.getFromCurrency(), 
                "La moneda de origen debería ser " + fromCurrency);
        assertEquals(toCurrency, resultado.getToCurrency(), 
                "La moneda de destino debería ser " + toCurrency);
        assertEquals(exchangeRate, resultado.getExchangeRate(), 
                "El tipo de cambio debería ser " + exchangeRate);
        assertNull(resultado.getId(), "El ID debería ser nulo en el mapeo");
    }

    @Test
    @DisplayName("Debería mapear correctamente con diferentes monedas")
    void deberiaMapearCorrectamente_ConDiferentesMonedas() {
        // Arrange - Preparar
        String desdeMXN = "MXN";
        String haciaUSD = "USD";
        Float tasaMXNUSD = 0.05f;
        
        ExternalAPIDTO dtoMXN = new ExternalAPIDTO(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                desdeMXN,
                haciaUSD,
                tasaMXNUSD
        );

        // Act - Actuar
        Exchange resultado = ExchangeMapper.toModel(dtoMXN);

        // Assert - Verificar
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(desdeMXN, resultado.getFromCurrency(), 
                "La moneda de origen debería ser " + desdeMXN);
        assertEquals(haciaUSD, resultado.getToCurrency(), 
                "La moneda de destino debería ser " + haciaUSD);
        assertEquals(tasaMXNUSD, resultado.getExchangeRate(), 
                "El tipo de cambio debería ser " + tasaMXNUSD);
    }

    @Test
    @DisplayName("Debería mapear correctamente con tipo de cambio cero")
    void deberiaMapearCorrectamente_ConTipoDeCambioCero() {
        // Arrange - Preparar
        Float tasaCero = 0.0f;
        
        ExternalAPIDTO dtoCero = new ExternalAPIDTO(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                fromCurrency,
                toCurrency,
                tasaCero
        );

        // Act - Actuar
        Exchange resultado = ExchangeMapper.toModel(dtoCero);

        // Assert - Verificar
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(tasaCero, resultado.getExchangeRate(), 
                "El tipo de cambio debería ser cero");
    }

    @Test
    @DisplayName("Debería mapear correctamente con tipo de cambio muy alto")
    void deberiaMapearCorrectamente_ConTipoDeCambioMuyAlto() {
        // Arrange - Preparar
        Float tasaAlta = 1000000.99f;
        
        ExternalAPIDTO dtoAlto = new ExternalAPIDTO(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                fromCurrency,
                toCurrency,
                tasaAlta
        );

        // Act - Actuar
        Exchange resultado = ExchangeMapper.toModel(dtoAlto);

        // Assert - Verificar
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(tasaAlta, resultado.getExchangeRate(), 
                "El tipo de cambio debería ser " + tasaAlta);
    }

    @Test
    @DisplayName("Debería ignorar campos adicionales del DTO y mapear solo los necesarios")
    void deberiaIgnorarCamposAdicionales_YMapearSoloLosNecesarios() {
        // Arrange - Preparar
        String resultado = "success";
        String documentacion = "https://www.exchangerate-api.com/docs";
        String terminosDeUso = "https://www.exchangerate-api.com/terms";
        
        ExternalAPIDTO dtoCompleto = new ExternalAPIDTO(
                resultado,
                documentacion,
                terminosDeUso,
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                fromCurrency,
                toCurrency,
                exchangeRate
        );

        // Act - Actuar
        Exchange resultadoMapeado = ExchangeMapper.toModel(dtoCompleto);

        // Assert - Verificar
        assertNotNull(resultadoMapeado, "El resultado no debería ser nulo");
        assertEquals(fromCurrency, resultadoMapeado.getFromCurrency(), 
                "La moneda de origen debería mapearse correctamente");
        assertEquals(toCurrency, resultadoMapeado.getToCurrency(), 
                "La moneda de destino debería mapearse correctamente");
        assertEquals(exchangeRate, resultadoMapeado.getExchangeRate(), 
                "El tipo de cambio debería mapearse correctamente");
        assertNull(resultadoMapeado.getId(), 
                "El ID no debería estar presente en el objeto mapeado");
    }

    @Test
    @DisplayName("Debería mapear correctamente con monedas de 3 caracteres")
    void deberiaMapearCorrectamente_ConMonedasDeTresCaracteres() {
        // Arrange - Preparar
        String desdeGBP = "GBP";
        String haciaJPY = "JPY";
        Float tasaGBPJPY = 150.25f;
        
        ExternalAPIDTO dtoGBP = new ExternalAPIDTO(
                "success",
                "https://www.exchangerate-api.com/docs",
                "https://www.exchangerate-api.com/terms",
                "1234567890",
                "Mon, 15 Jun 2026 00:00:01 +0000",
                "1234567920",
                "Tue, 16 Jun 2026 00:00:01 +0000",
                desdeGBP,
                haciaJPY,
                tasaGBPJPY
        );

        // Act - Actuar
        Exchange resultado = ExchangeMapper.toModel(dtoGBP);

        // Assert - Verificar
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(desdeGBP, resultado.getFromCurrency(), 
                "La moneda de origen debería ser " + desdeGBP);
        assertEquals(haciaJPY, resultado.getToCurrency(), 
                "La moneda de destino debería ser " + haciaJPY);
        assertEquals(tasaGBPJPY, resultado.getExchangeRate(), 
                "El tipo de cambio debería ser " + tasaGBPJPY);
        assertEquals(3, resultado.getFromCurrency().length(), 
                "La moneda de origen debería tener 3 caracteres");
        assertEquals(3, resultado.getToCurrency().length(), 
                "La moneda de destino debería tener 3 caracteres");
    }
}
