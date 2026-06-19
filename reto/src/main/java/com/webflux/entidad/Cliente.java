package com.webflux.entidad;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Table(name = "tbl_cliente")
public class Cliente  {

    @Id
    private Long id;

    @Column("cliente_id")
    private String clienteId;

    @Column("monto")
    private Double monto;

    @Column("tasa")
    private Double tasa;

    @Column("moneda_origen")
    @Size(min = 3, max = 3)
    private String monedaOrigen;

    @Column("moneda_destino")
    @Size(min = 3, max = 3)
    private String monedaDestino;
}
