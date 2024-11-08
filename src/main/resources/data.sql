-- Insertando autores
INSERT INTO autor (nombre, nacionalidad)
VALUES ('Gabriel García Márquez', 'Colombiana');
INSERT INTO autor (nombre, nacionalidad)
VALUES ('Isabel Allende', 'Chilena');

-- Insertando libros
INSERT INTO libro (titulo, genero, anyo_publicacion, estado, autor_id)
VALUES ('Cien años de soledad', 'Novela', 1967, 'disponible', 1),
       ('El amor en los tiempos del cólera', 'Novela', 1985, 'disponible', 1),
       ('La casa de los espíritus', 'Novela', 1982, 'disponible', 2);

-- Insertando usuarios
INSERT INTO usuario (nombre, email, password, rol)
VALUES ('Usuario1', 'usuario1@example.com', '123456', 'usuario'),
       ('Bibliotecario1', 'bibliotecario1@example.com', '123456', 'bibliotecario');

-- Insertando préstamos
INSERT INTO prestamo (libro_id, usuario_id, fecha_prestamo, fecha_devolucion)
VALUES ( 1, 1, '2024-01-10', NULL),
       (2, 1, '2024-01-12', NULL);
