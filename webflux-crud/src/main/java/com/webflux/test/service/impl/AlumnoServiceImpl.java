package com.webflux.test.service.impl;

import org.springframework.stereotype.Service;

import com.webflux.test.dto.AlumnoRequestDTO;
import com.webflux.test.dto.AlumnoResponseDTO;
import com.webflux.test.exception.AlumnoNotFoundException;
import com.webflux.test.mapper.AlumnoMapper;
import com.webflux.test.model.Alumno;
import com.webflux.test.repository.IAlumnoRepository;
import com.webflux.test.service.IAlumnoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

    private final IAlumnoRepository alumnoRepository;

    public AlumnoServiceImpl(IAlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Override
    public Mono<AlumnoResponseDTO> create(AlumnoRequestDTO alumno) {
        Alumno nuevoAlumno = AlumnoMapper.toEntity(alumno);
        return alumnoRepository.save(nuevoAlumno)
                .map(AlumnoMapper::toResponseDTO);
    }

    @Override
    public Mono<AlumnoResponseDTO> update(Long id, AlumnoRequestDTO alumno) {
        return alumnoRepository.findById(id)
                .switchIfEmpty(Mono.error(new AlumnoNotFoundException(id)))
                .then(Mono.just(AlumnoMapper.toEntity(id, alumno)))
                .flatMap(alumnoRepository::save)
                .map(AlumnoMapper::toResponseDTO);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return alumnoRepository.findById(id)
                .switchIfEmpty(Mono.error(new AlumnoNotFoundException(id)))
                .then(alumnoRepository.deleteById(id));
    }

    @Override
    public Mono<AlumnoResponseDTO> findById(Long id) {
        return alumnoRepository.findById(id)
                .map(AlumnoMapper::toResponseDTO)
                .switchIfEmpty(Mono.error(new AlumnoNotFoundException(id)));
    }

    @Override
    public Flux<AlumnoResponseDTO> findAll() {
        return alumnoRepository.findAll()
                .map(AlumnoMapper::toResponseDTO);
    }
}