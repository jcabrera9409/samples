package com.webflux.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumnoResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
}