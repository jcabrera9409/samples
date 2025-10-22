package com.webflux.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("exchange_data")
public class Exchange {

    @Id
    private Long id;

    @Column("from_currency")
    @NotBlank(message = "From currency must not be blank")
    @Size(min = 3, max = 3, message = "From currency must be exactly 3 characters")
    private String fromCurrency;

    @Column("to_currency")
    @NotBlank(message = "To currency must not be blank")
    @Size(min = 3, max = 3, message = "To currency must be exactly 3 characters")
    private String toCurrency;

    @Column("exchange_rate")
    private Float exchangeRate;

}