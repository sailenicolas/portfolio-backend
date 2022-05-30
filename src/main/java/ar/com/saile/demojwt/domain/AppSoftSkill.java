package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public String toString() {

        return "AppSoftSkill{" +
                "id=" + id +
                ", val=" + val +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppSoftSkill)) return false;
        AppSoftSkill that = (AppSoftSkill) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    public AppSoftSkill(Long val, String titulo, String descripcion) {

        this.val = val;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    @NotNull
    @Column(nullable = false)
    @Min(0)
    @Max(100)
    private Long val;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String titulo;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private AppUser userApp;

}
