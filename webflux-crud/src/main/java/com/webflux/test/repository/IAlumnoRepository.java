package com.webflux.test.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.webflux.test.model.Alumno;

public interface IAlumnoRepository extends R2dbcRepository<Alumno, Long> {
}