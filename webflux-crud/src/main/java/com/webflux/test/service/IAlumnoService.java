package com.webflux.test.service;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlumnoService {
    Mono<AlumnoResponseDTO> create(AlumnoRequestDTO alumno);
    Mono<AlumnoResponseDTO> update(Long id,AlumnoRequestDTO alumno);
    Mono<Void> delete(Long id);
    Mono<AlumnoResponseDTO> findById(Long id);
    Flux<AlumnoResponseDTO> findAll();
}