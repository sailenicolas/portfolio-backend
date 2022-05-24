package ar.com.saile.demojwt.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor

public class AppSoftSkill implements Serializable {
    private final static String ID_COLUMN = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    public AppSoftSkill(Long val, String titulo, String descripcion) {
        this.val = val;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    private Long val;
    private String titulo;
    private String descripcion;
}
