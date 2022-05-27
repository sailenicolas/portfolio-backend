package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

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

    private String institucion;

    @Column(nullable = false)

    private String titulo;

    @NotNull
    @Column(nullable = false)
    private String imagen;

    @NotNull
    @Column(nullable = false)
    private String carrera;

    @NotNull
    @Column(nullable = false)
    private Integer puntaje;

    @Column(nullable = false)
    @NotNull
    @Past
    private Date inicio;

    private Date fin;

    @ManyToOne()
    @JsonBackReference
    @NotNull
    private AppUser userApp;

    public AppEducation(String institucion, String titulo, String imagen, String carrera, Integer puntaje, Date inicio, Date fin) {

        this.institucion = institucion;
        this.titulo = titulo;
        this.imagen = imagen;
        this.carrera = carrera;
        this.puntaje = puntaje;
        this.inicio = inicio;
        this.fin = fin;
    }

}
