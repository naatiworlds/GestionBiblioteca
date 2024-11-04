package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    @Autowired
    public LibroRepository libroRepository;

    public List<Libro> getAllLibros(){
        return libroRepository.findAll();
    }
    public Libro getLibroById(Long id){
        return libroRepository.getReferenceById(id);
    }

    public Libro saveLibro(Libro libro){
        return libroRepository.save(libro);
    }

    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
