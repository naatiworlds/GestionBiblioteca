package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Page<Libro> listarLibros(String titulo, String genero, Pageable pageable){
        if (titulo != null && genero != null) {
            return libroRepository.findByTituloContainingIgnoreCaseAndGeneroIgnoreCase(titulo, genero, pageable);
        }
        else if (titulo != null) {
            return libroRepository.findLibrosByTituloContainingIgnoreCase(titulo, pageable);
        } else if (genero != null) {
            return libroRepository.findByGeneroIgnoreCase(genero, pageable);
        } else {
            return libroRepository.findAll(pageable);
        }
    }

public Optional<Libro> obtenerLibro(Long id){
        return libroRepository.findById(id);
    }

    public Libro crearLibro(Libro libro){
        return libroRepository.save(libro);
    }

    public Libro actualizarLibro(Long id, Libro libroActualizado){
        return libroRepository.findById(id)
                .map(libro -> {
                    libro.setTitulo(libroActualizado.getTitulo());
                    libro.setGenero(libroActualizado.getGenero());
                    libro.setAutor(libroActualizado.getAutor());
                    libro.setAnyoPublicacion(libroActualizado.getAnyoPublicacion());
                    libro.setEstado(libroActualizado.getEstado());
                    return libroRepository.save(libro);
                }).orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id " + id));
    }

    public Libro actualizarLibro2(Long id, Libro libroActualizado) {
        return libroRepository.findById(id).map(libro -> {
            // Comprobación de campos no nulos
            if (libroActualizado.getTitulo() != null) {
                libro.setTitulo(libroActualizado.getTitulo());
            }
            if (libroActualizado.getGenero() != null) {
                libro.setGenero(libroActualizado.getGenero());
            }
            if (libroActualizado.getAnyoPublicacion() != null) {
                libro.setAnyoPublicacion(libroActualizado.getAnyoPublicacion());
            }
            if (libroActualizado.getEstado() != null) {
                libro.setEstado(libroActualizado.getEstado());
            }
            if (libroActualizado.getAutor() != null) {
                libro.setAutor(libroActualizado.getAutor());
            }

            return libroRepository.save(libro);
        }).orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id " + id));
    }

    public Libro actualizarLibro3(Long id, Libro libroActualizado) {
        return libroRepository.findById(id).map(libro -> {
            Optional.ofNullable(libroActualizado.getTitulo()).ifPresent(libro::setTitulo);
            Optional.ofNullable(libroActualizado.getGenero()).ifPresent(libro::setGenero);
            Optional.ofNullable(libroActualizado.getAnyoPublicacion()).ifPresent(libro::setAnyoPublicacion);
            Optional.ofNullable(libroActualizado.getEstado()).ifPresent(libro::setEstado);
            Optional.ofNullable(libroActualizado.getAutor()).ifPresent(libro::setAutor);

            return libroRepository.save(libro);
        }).orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id " + id));
    }

    public void borrarLibro(Long id){
        if(!libroRepository.existsById(id)){
            throw new NoSuchElementException("Libro no encontrado con id " + id);
        }
        libroRepository.deleteById(id);
    }
}
