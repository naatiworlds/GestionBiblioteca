package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.repositories.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroRepository libroRepository;

    public LibroController (LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public Page<Libro> listasLibros(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String genero,
            Pageable pageable
    ){
        // get http://localhost:8080/api/libros?titulo=el&genero=terror&page=0
        if(titulo != null && genero != null){
            return libroRepository.findLibroByTituloContainingIgnoreCaseAndGeneroContainingIgnoreCase(titulo, genero, pageable);
        }else if(titulo != null){
            return libroRepository.findLibroByTituloContainingIgnoreCase(titulo, pageable);
        }else if(genero != null){
            return libroRepository.findLibroByGeneroContainingIgnoreCase(genero, pageable);
        }else{
            return libroRepository.findAll(pageable);
        }
    }

    // Obtener un libro por id
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Long id){
        return libroRepository.findById(id)
                .map(libro -> ResponseEntity.ok().body(libro))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un numero de libros
    @PostMapping
    public ResponseEntity<Libro> createLibro(@RequestBody @Valid Libro libro){
        return ResponseEntity.ok(libroRepository.save(libro));
    }

    // Actualizar un libro
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(
            @PathVariable Long id,
            @RequestBody @Valid Libro libro
    ){
        return libroRepository.findById(id)
                .map(libroData -> {
                    libroData.setTitulo(libro.getTitulo());
                    libroData.setGenero(libro.getGenero());
                    libroData.setAñoPublicacion(libro.getAñoPublicacion());
                    libroData.setEstado(libro.getEstado());
                    libroData.setAutor(libro.getAutor());
                    return ResponseEntity.ok(libroRepository.save(libroData));
                }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id){
        return libroRepository.findById(id)
                .map(libro -> {
                    libroRepository.delete(libro);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

}