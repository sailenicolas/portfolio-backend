package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppProject implements Serializable {

    private final static String ID_COLUMN = "id";

    public AppProject(String titulo, String imagen, String descripcion) {

        this.titulo = titulo;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppProject that)) return false;
        return getId().equals(that.getId());
    }

    @Override
    public String toString() {

        return "AppProject{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Column(nullable = false)
    @NotNull
    private String titulo;

    private String imagen;

    @Column(nullable = false)
    @NotNull
    private String descripcion;

    @ManyToOne()
    @JsonBackReference
    private AppUser userApp;

}
