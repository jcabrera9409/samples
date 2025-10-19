package com.webflux.test.mapper;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.model.Alumno;

public class AlumnoMapper {
    
    public static Alumno toEntity(AlumnoRequestDTO dto) {
        return Alumno.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .edad(dto.getEdad())
                .email(dto.getEmail())
                .build();
    }

    public static Alumno toEntity(Long id, AlumnoRequestDTO dto) {
        return Alumno.builder()
                .id(id)
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .edad(dto.getEdad())
                .email(dto.getEmail())
                .build();
    }

    public static AlumnoResponseDTO toResponseDTO(Alumno entity) {
        return AlumnoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .edad(entity.getEdad())
                .email(entity.getEmail())
                .build();
    }
}