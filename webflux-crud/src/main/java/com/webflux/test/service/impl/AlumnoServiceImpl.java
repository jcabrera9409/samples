package com.webflux.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Mono<Alumno> create(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public Mono<Alumno> update(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return alumnoRepository.deleteById(id);
    }

    @Override
    public Mono<Alumno> findById(Long id) {
        return alumnoRepository.findById(id);
    }

    @Override
    public Flux<Alumno> findAll() {
        return alumnoRepository.findAll();
    }
}