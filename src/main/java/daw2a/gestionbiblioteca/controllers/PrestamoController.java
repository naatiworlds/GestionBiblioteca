package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Prestamo;
import daw2a.gestionbiblioteca.services.PrestamoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<Prestamo> listaPrestamos() {
        return prestamoService.gestAllPrestamos();
    }
}
