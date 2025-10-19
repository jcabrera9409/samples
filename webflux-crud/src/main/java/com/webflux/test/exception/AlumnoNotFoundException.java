package com.webflux.test.exception;

public class AlumnoNotFoundException extends RuntimeException {
    
    public AlumnoNotFoundException(String message) {
        super(message);
    }
    
    public AlumnoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AlumnoNotFoundException(Long id) {
        super("Alumno con ID " + id + " no encontrado");
    }
}