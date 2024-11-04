package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Prestamo;
import daw2a.gestionbiblioteca.repositories.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoService {
    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Prestamo> gestAllPrestamos(){
        return prestamoRepository.findAll();
    }
}
