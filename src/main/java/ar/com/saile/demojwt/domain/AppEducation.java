package ar.com.saile.demojwt.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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
    private String institucion;
    private String titulo;
    private String imagen;

    public AppEducation(String institucion, String titulo, String imagen, String carrera, Integer puntaje, Date inicio, Date fin) {

        this.institucion = institucion;
        this.titulo = titulo;
        this.imagen = imagen;
        this.carrera = carrera;
        this.puntaje = puntaje;
        this.inicio = inicio;
        this.fin = fin;
    }

    private String carrera;
    private Integer puntaje;
    private Date inicio;
    private Date fin;
}
