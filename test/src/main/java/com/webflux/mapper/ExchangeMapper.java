package com.webflux.mapper;

import com.webflux.dto.ExternalAPIDTO;
import com.webflux.model.Exchange;

public class ExchangeMapper {

    public static Exchange toModel(ExternalAPIDTO dto) {
        return Exchange.builder()
                .fromCurrency(dto.base_code())
                .toCurrency(dto.target_code())
                .exchangeRate(dto.conversion_rate())
                .build();
    }
}