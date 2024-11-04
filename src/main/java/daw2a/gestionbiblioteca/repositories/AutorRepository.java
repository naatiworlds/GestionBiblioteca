package daw2a.gestionbiblioteca.repositories;

import daw2a.gestionbiblioteca.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {}