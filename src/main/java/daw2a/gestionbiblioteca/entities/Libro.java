package daw2a.gestionbiblioteca.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    @ManyToOne()
    @JoinColumn(name = "autor_id")
    @JsonBackReference // Lado no propietario esto sirve para cortar el bucle
    private Autor autor;

    private String genero;
    private LocalDate añoPublicacion;

    @Enumerated(EnumType.STRING)
    private EstadoLibro estado;
}
