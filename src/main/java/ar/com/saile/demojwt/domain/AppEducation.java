package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppEducation implements Serializable {

    @Override
    public String toString() {

        return "AppEducation{" +
                "id=" + id +
                ", institucion='" + institucion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", carrera='" + carrera + '\'' +
                ", puntaje=" + puntaje +
                ", inicio=" + inicio +
                ", fin=" + fin +
                '}';
    }

    private final static String ID_COLUMN = "id";

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppEducation)) return false;
        AppEducation that = (AppEducation) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "No puede estar en blanco")
    @NotNull
    private String institucion;

    @Column(nullable = false)
    @NotBlank(message = "No puede estar en blanco")
    @NotNull
    private String titulo;

    @Column()
    private String imagen;

    @NotBlank(message = "No puede estar en blanco")
    @NotNull
    @Column(nullable = false)
    private String carrera;

    @NotNull(message = "No puede estar en blanco")
    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Integer puntaje;

    @Column(nullable = false)
    @NotNull(message = "No puede estar en blanco")
    @Past
    private LocalDate inicio;

    @Nullable
    @PastOrPresent
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
