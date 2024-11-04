package daw2a.gestionbiblioteca.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "libro_id", "fechaPrestamo"})
)*/
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    /*@Column(unique = true, nullable = false)*/
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    /*@Column(unique = true, nullable = false)*/

    private Libro libro;
    /*@Column(unique = true, nullable = false)*/

    private Date fechaPrestamo;
    private Date fechaDevolucion;
}
