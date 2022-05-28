package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppEducation implements Serializable {

    private final static String ID_COLUMN = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "No puede estar en blanco")
    private String institucion;

    @Column(nullable = false)
    @NotBlank(message = "No puede estar en blanco")
    private String titulo;

    @Column()
    private String imagen;

    @NotBlank(message = "No puede estar en blanco")
    @Column(nullable = false)
    private String carrera;

    @NotNull(message = "No puede estar en blanco")
    @Column(nullable = false)
    private Integer puntaje;

    @Column(nullable = false)
    @NotNull(message = "No puede estar en blanco")
    @Past
    private LocalDate inicio;

    @Nullable
    private LocalDate fin;

    @ManyToOne()
    @JsonBackReference
    private AppUser userApp;

    public AppEducation(String institucion, String titulo, String imagen, String carrera, Integer puntaje, @Past LocalDate inicio, LocalDate fin) {

        this.institucion = institucion;
        this.titulo = titulo;
        this.imagen = imagen;
        this.carrera = carrera;
        this.puntaje = puntaje;
        this.inicio = inicio;
        this.fin = fin;
    }

}
