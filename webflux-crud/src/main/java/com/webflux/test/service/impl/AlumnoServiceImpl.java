package com.webflux.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.model.Alumno;
import com.webflux.test.repository.IAlumnoRepository;
import com.webflux.test.service.IAlumnoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    private IAlumnoRepository alumnoRepository;

    @Override
    public Mono<AlumnoResponseDTO> create(AlumnoRequestDTO alumno) {
        Alumno nuevoAlumno = Alumno.builder()
                .nombre(alumno.getNombre())
                .apellido(alumno.getApellido())
                .edad(alumno.getEdad())
                .email(alumno.getEmail())
                .build();
        return alumnoRepository.save(nuevoAlumno)
                .map(savedAlumno -> AlumnoResponseDTO.builder()
                        .id(savedAlumno.getId())
                        .nombre(savedAlumno.getNombre())
                        .apellido(savedAlumno.getApellido())
                        .edad(savedAlumno.getEdad())
                        .email(savedAlumno.getEmail())
                        .build());
    }

    @Override
    public Mono<AlumnoResponseDTO> update(Long id, AlumnoRequestDTO alumno) {
        Alumno alumnoActualizado = Alumno.builder()
                .id(id)
                .nombre(alumno.getNombre())
                .apellido(alumno.getApellido())
                .edad(alumno.getEdad())
                .email(alumno.getEmail())
                .build();
        return alumnoRepository.save(alumnoActualizado)
                .map(savedAlumno -> AlumnoResponseDTO.builder()
                        .id(savedAlumno.getId())
                        .nombre(savedAlumno.getNombre())
                        .apellido(savedAlumno.getApellido())
                        .edad(savedAlumno.getEdad())
                        .email(savedAlumno.getEmail())
                        .build());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return alumnoRepository.deleteById(id);
    }

    @Override
    public Mono<AlumnoResponseDTO> findById(Long id) {
        return alumnoRepository.findById(id)
                .map(alumno -> AlumnoResponseDTO.builder()
                        .id(alumno.getId())
                        .nombre(alumno.getNombre())
                        .apellido(alumno.getApellido())
                        .edad(alumno.getEdad())
                        .email(alumno.getEmail())
                        .build());
    }

    @Override
    public Flux<AlumnoResponseDTO> findAll() {
        return alumnoRepository.findAll()
                .map(alumno -> AlumnoResponseDTO.builder()
                        .id(alumno.getId())
                        .nombre(alumno.getNombre())
                        .apellido(alumno.getApellido())
                        .edad(alumno.getEdad())
                        .email(alumno.getEmail())
                        .build());
    }
}