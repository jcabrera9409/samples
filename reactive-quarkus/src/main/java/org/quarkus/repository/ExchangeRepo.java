package org.quarkus.repository;

import org.quarkus.model.Exchange;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExchangeRepo implements PanacheRepository<Exchange> {
    @WithSession
    public Uni<Exchange> findByFromAndToCurrency(String from, String to) {
        return find("fromCurrency = ?1 and toCurrency = ?2", from, to).firstResult();
    }

    @WithTransaction
    public Uni<Exchange> save(Exchange exchange) {
        return persist(exchange);
    }
}