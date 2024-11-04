package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Autor;
import daw2a.gestionbiblioteca.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> getAllAutors(){
        return autorRepository.findAll();
    }

    public Autor saveAutor(Autor autor){

        return autorRepository.save(autor);
    }

    public void deleteAutor(int id) {
        autorRepository.deleteById(id);
    }
}
