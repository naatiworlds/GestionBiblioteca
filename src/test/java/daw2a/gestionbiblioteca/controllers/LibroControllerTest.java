package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Libro;
import daw2a.gestionbiblioteca.repositories.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroControllerTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroController libroController;

    private Libro libro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libro = new Libro(1L, "Cien años de soledad",
                "Novela", "1967", "disponible", null);

    }

    @Test
    void listarLibros() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Libro> page = new PageImpl<>(Arrays.asList(libro));
        when(libroRepository.findAll(pageable)).thenReturn(page);

        Page<Libro> result = libroController.listarLibros(null, null, pageable).getBody();

        assertEquals(1, result.getTotalElements());
        verify(libroRepository,times(1)).findAll(pageable);
    }

    @Test
    void obtenerLibro() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        ResponseEntity<Libro> response = libroController.obtenerLibro(1L);

        assertEquals(ResponseEntity.ok(libro), response);
        assertEquals(libro, response.getBody());
        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    void crearLibro() {
        when(libroRepository.save(libro)).thenReturn(libro);

        ResponseEntity<Libro> response = libroController.crearLibro(libro);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(libro, response.getBody());
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    void actualizarLibro() {
        // Crear libro original en el repositorio
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        // Crear libro actualizado con nuevos valores
        Libro updatedLibro = new Libro(1L, "Cien años de soledad",
                "Novela", "1967", "prestado", null);

        // Configurar el comportamiento del mock de save() para devolver el libro actualizado
        when(libroRepository.save(libro)).thenReturn(updatedLibro);

        // Llamar al método actualizarLibro en el controlador
        ResponseEntity<Libro> response = libroController.actualizarLibro(1L, updatedLibro);

        // Verificar el código de estado de la respuesta y que el cuerpo contenga el libro actualizado
        assertEquals(HttpStatus.OK, response.getStatusCode(), "El código de estado no es 200 OK");
        assertEquals(updatedLibro, response.getBody(), "El libro actualizado no coincide con la respuesta");

        // Verificar interacciones con el repositorio
        verify(libroRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    void eliminarLibro() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        doNothing().when(libroRepository).delete(libro);

        ResponseEntity<?> response = libroController.eliminarLibro(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(libroRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).delete(libro);
    }
}