package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Autor;
import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<Autor> getAll() {
        return autorService.getAllAutors();
    }


    @PostMapping("/load")
    public ResponseEntity<Autor> addAuthor(@RequestBody Autor author) {
        Autor newAuthor = autorService.saveAutor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAutor(@PathVariable int id) {
        autorService.deleteAutor(id);
    }
}
