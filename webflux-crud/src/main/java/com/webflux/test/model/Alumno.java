package com.webflux.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "alumnos")
public class Alumno {
    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
}