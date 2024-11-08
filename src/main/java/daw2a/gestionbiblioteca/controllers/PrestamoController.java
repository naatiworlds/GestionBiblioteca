package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Prestamo;
import daw2a.gestionbiblioteca.repositories.PrestamoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoRepository prestamoRepository;

    public PrestamoController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    // Listar todos los préstamos
    @GetMapping
    public ResponseEntity<Page<Prestamo>> listarPrestamos(Pageable pageable) {
        return ResponseEntity.ok(prestamoRepository.findAll(pageable));
    }

    // Obtener los detalles de un préstamo específico
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable Long id) {
        return prestamoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo préstamo
    @PostMapping
    public ResponseEntity<Prestamo> crearPrestamo(@RequestBody Prestamo prestamo) {
        //cambiar estado del libro a prestado
        //buscar si el libro está prestado devolver error
        //
        prestamo.getLibro().setEstado("prestado");
        return ResponseEntity.ok(prestamoRepository.save(prestamo));
    }

    // Actualizar un préstamo existente
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizarPrestamo(@PathVariable Long id,
                                                       @RequestBody Prestamo prestamoActualizado) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    prestamo.setFechaPrestamo(prestamoActualizado.getFechaPrestamo());
                    prestamo.setFechaDevolucion(prestamoActualizado.getFechaDevolucion());
                    return ResponseEntity.ok(prestamoRepository.save(prestamo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Long id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    //cambiar estado del libro a disponible
                    prestamo.getLibro().setEstado("disponible");
                    prestamo.setFechaDevolucion(java.time.LocalDate.now());
                    return ResponseEntity.ok(prestamoRepository.save(prestamo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un préstamo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPrestamo(@PathVariable Long id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> {
                    prestamoRepository.delete(prestamo);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
