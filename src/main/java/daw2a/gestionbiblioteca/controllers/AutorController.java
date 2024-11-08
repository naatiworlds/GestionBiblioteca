package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Autor;
import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.services.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/autores")
@CrossOrigin(origins = "http://example.com")
public class AutorController {
    private final AutorService autorService;
    private final HandlerMapping resourceHandlerMapping;

    @Autowired
    public AutorController(AutorService autorService, @Qualifier("resourceHandlerMapping") HandlerMapping resourceHandlerMapping) {
        this.autorService = autorService;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }

    //Listar todos los autores
    @GetMapping
    @CrossOrigin(origins = "http://example.com")
    public ResponseEntity<Page<Autor>> listarAutores(@RequestParam(required = false) String nombre,
                                                     Pageable pageable) {
        Page<Autor> autors = autorService.listarAutores(nombre, pageable);
        return ResponseEntity.ok(autors);
    }

    //Obtener los detalles de un autor específico
    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtenerAutor(@PathVariable Long id) {
        try {
            Autor autor = autorService.obtenerAutor(id)
                    .orElseThrow(() -> new NoSuchElementException("Autor no encontrado con id " + id));
            return ResponseEntity.ok(autor);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Crear un nuevo autor
    @PostMapping
    public ResponseEntity<Autor> crearAutor(@RequestBody @Valid Autor autor) {
        Autor nuevoAutor = autorService.crearAutor(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(autor);
    }

    // Actualizar un autor existente
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizarAutor(@PathVariable Long id,
                                                 @RequestBody Autor autorActualizado) {
        try {
            Autor autor = autorService.actualizarAutor(id, autorActualizado);
            return ResponseEntity.ok(autor);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Eliminar un autor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAutor(@PathVariable Long id) {
        try {
            autorService.borrarAutor(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
