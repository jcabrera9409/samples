package org.quarkus.controller;

import org.quarkus.service.ExchangeService;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

@Path("/api/v1/exchange")
@Produces("application/json")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GET
    @Path("/{from}/{to}")
    public Uni<Float> getExchangeRate(@PathParam("from") String fromCurrency, @PathParam("to") String toCurrency) {
        return exchangeService.getExchangeRate(fromCurrency, toCurrency);
    }
}