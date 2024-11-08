package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.repositories.LibroRepository;
import daw2a.gestionbiblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    //Listar todos los libros con paginación y filtros opcionales
    @GetMapping
    public ResponseEntity<Page<Libro>> listarLibros(@RequestParam(required = false) String titulo,
                                    @RequestParam(required = false) String genero,
                                    Pageable pageable){
       Page<Libro> libros = libroService.listarLibros(titulo, genero, pageable);
       return ResponseEntity.ok(libros);
    }

    // Obtener los detalles de un libro específico
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Long id){
        try {
            Libro libro = libroService.obtenerLibro(id)
                    .orElseThrow(() -> new NoSuchElementException("Libro no encontrado con id " + id));
            return ResponseEntity.ok(libro);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Crear un nuevo libro (sólo bibliotecario)
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody @Valid Libro libro)
    {
        Libro nuevoLibro = libroService.crearLibro(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoLibro);
    }
    // Actualizar un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id,
                                                 @RequestBody Libro libroActualizado) {
        try {
            Libro libro = libroService.actualizarLibro(id, libroActualizado);
            return ResponseEntity.ok(libro);

        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //Eliminar un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id)
    {
        try{
            libroService.borrarLibro(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
