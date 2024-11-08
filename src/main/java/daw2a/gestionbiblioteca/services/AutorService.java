package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Autor;
import daw2a.gestionbiblioteca.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Page<Autor> listarAutores(String nombre, Pageable pageable) {
        if (nombre != null) {
            return autorRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return autorRepository.findAll(pageable);
    }

    public Optional<Autor> obtenerAutor(Long id) {
        return autorRepository.findById(id);
    }

    public Autor crearAutor(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor actualizarAutor(Long id, Autor autorActualizado) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNombre(autorActualizado.getNombre());
                    autor.setNacionalidad(autorActualizado.getNacionalidad());
                    return autorRepository.save(autor);
                })
                .orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id " + id));
    }
    public void borrarAutor(Long id){
        if(!autorRepository.existsById(id)){
            throw new NoSuchElementException("Libro no encontrado con id " + id);
        }
        autorRepository.deleteById(id);
    }
}
