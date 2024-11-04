package daw2a.gestionbiblioteca.repositories;

import daw2a.gestionbiblioteca.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario>findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    Optional<Usuario> findUsuarioByEmail(String email);
    // Busqueda de usuario por email
    /*Optional<Usuario> findUsuarioByEmail(String email);*/
}