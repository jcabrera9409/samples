package com.webflux.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoRequestDTO {
    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;
    @NotBlank
    @Size(min = 2, max = 100)
    private String apellido;
    @Min(1)
    private int edad;
    @Email
    private String email;
}