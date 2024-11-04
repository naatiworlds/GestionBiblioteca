package daw2a.gestionbiblioteca.controllers;

import daw2a.gestionbiblioteca.entities.Usuario;
import daw2a.gestionbiblioteca.repositories.UsuarioRepository;
import daw2a.gestionbiblioteca.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(@RequestParam(required = false)String nombre,
                                                        Pageable pageable){
        if (nombre != null) {
            return ResponseEntity.ok(usuarioRepository.findByNombreContainingIgnoreCase(nombre,pageable));
        }
        return ResponseEntity.ok(usuarioRepository.findAll(pageable));
    }

    //obtener los detalles de un autor especifico
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id){//asui indicamos que es una variable del path o ruta, en este caso /autores/{id}
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok) // si lo encuentra para aqui
                .orElse(ResponseEntity.notFound().build()); // si no lo encuentra devuelve un notFound
    }
    @GetMapping("/me")
    public ResponseEntity<ResponseEntity<Usuario>>obtenerPerfil(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioRepository.findUsuarioByEmail(email)
                    .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build())
        );
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    //Actualizar un autor
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,
                                                     @RequestBody Usuario usuarioActualizado){
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    //Eliminar autor
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarAutor(@PathVariable Long id){
        return usuarioRepository.findById(id).map(autor -> {
            usuarioRepository.delete(autor);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
