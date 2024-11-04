package daw2a.gestionbiblioteca.repositories;

import daw2a.gestionbiblioteca.entities.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Busqueda de libros por titulo
    Page<Libro> findLibroByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    // Busqueda por genero
    Page<Libro> findLibroByGeneroContainingIgnoreCase(String genero, Pageable pageable);

    // Busqueda por titulo y genero
    Page<Libro> findLibroByTituloContainingIgnoreCaseAndGeneroContainingIgnoreCase(String titulo, String genero, Pageable pageable);

}