package daw2a.gestionbiblioteca.services;

import daw2a.gestionbiblioteca.entities.Usuario;
import daw2a.gestionbiblioteca.exceptions.RecursoNoEncontradoException;
import daw2a.gestionbiblioteca.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Usuario> listarUsuarios(String nombre, Pageable pageable) {
        if (nombre != null) {
            return usuarioRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return usuarioRepository.findAll(pageable);
    }

    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario registarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerMiPerfil(String email) {
        return usuarioRepository.findUsuarioByEmail(email);

    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setRol(usuarioActualizado.getRol());
                    usuario.setPassword(usuarioActualizado.getPassword());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con id" + id));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id " + id);
        }
    }
}
