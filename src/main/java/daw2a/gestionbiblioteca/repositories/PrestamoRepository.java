package daw2a.gestionbiblioteca.repositories;

import daw2a.gestionbiblioteca.entities.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Obtener prestamos de un usuario especifico
    /*List<Prestamo> findPrestamoBy(Long usuarioId);*/

    // Obtener prestamos para un libro especifico
    /*List<Prestamo> findPrestamoByLibroId(Long libroId);*/

    // Obtener Prestamos para un libro por nombre
    //@Query("SELECT p FROM Prestamo p WHERE p.libro.titulo LIKE %?1%")
    //List<Prestamo> findPrestamoByLibroTituloContainingIgnoreCase(@Param("titulo") String titulo);
}