-- Crear tabla alumnos
CREATE TABLE IF NOT EXISTS alumnos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT,
    email VARCHAR(150) UNIQUE
);

-- Insertar registros de ejemplo
INSERT INTO alumnos (nombre, apellido, edad, email)
VALUES
('Juan', 'Pérez', 21, 'juan.perez@example.com'),
('María', 'Gómez', 22, 'maria.gomez@example.com'),
('Carlos', 'Torres', 23, 'carlos.torres@example.com');
