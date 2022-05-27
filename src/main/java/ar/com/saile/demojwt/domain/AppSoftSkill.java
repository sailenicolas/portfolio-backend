package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @NotNull
    @Column(nullable = false)

    private Long val;

    @Column(nullable = false)
    @NotNull
    private String titulo;

    @Column(nullable = false)
    @NotNull
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private AppUser userApp;

    @Override
    public String toString() {

        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "val = " + val + ", " +
                "titulo = " + titulo + ", " +
                "descripcion = " + descripcion + ")";
    }
}
