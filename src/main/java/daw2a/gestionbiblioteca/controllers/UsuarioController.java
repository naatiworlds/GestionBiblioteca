package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Usuario;
import daw2a.gestionbiblioteca.services.UsuarioService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final ConcurrentMapCacheManager cacheManager;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, ConcurrentMapCacheManager cacheManager) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }


    // Listar todos los usuarios con paginación y filtros opcionales
    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(@RequestParam(required = false) String nombre, Pageable pageable) {
        Page<Usuario> usuario = usuarioService.listarUsuarios(nombre, pageable);
        return ResponseEntity.ok(usuario);
    }

    // Obtener los detalles de un usuario específico
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuario(id)
                    .orElseThrow(()-> new NoSuchElementException("Usuario no encontrado con id " + id));
            return ResponseEntity.ok(usuario);
        }catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody @Valid Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario nuevoUsuario = usuarioService.registarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerMiPerfil(@RequestParam("email") String email) {
        try {
            Usuario usuario = usuarioService.obtenerMiPerfil(email)
                    .orElseThrow(()-> new NoSuchElementException("Usuario no encontrado con email " + email));
            return ResponseEntity.ok(usuario);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,
                                                     @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
            return ResponseEntity.ok(usuario);
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
