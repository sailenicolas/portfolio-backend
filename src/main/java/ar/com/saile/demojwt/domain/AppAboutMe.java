package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppAboutMe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    private String imagen;

    @Nullable
    private String header;

    @NotNull
    @NotBlank
    private String sobremi;

    @NotNull
    @NotBlank
    private String nombre;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppAboutMe)) return false;
        AppAboutMe that = (AppAboutMe) o;
        return getId().equals(that.getId()) && getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getEmail());
    }

    private String ubicacion;

    @Column(unique = true, nullable = false)
    @Email(message = "Email no valido")
    @NotNull
    @NotEmpty
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private AppUser appUser;

    public AppAboutMe(String imagen, String header, String aboutMe, String name, String ubicacion, String email) {

        this.imagen = imagen;
        this.header = header;
        this.sobremi = aboutMe;
        this.nombre = name;
        this.ubicacion = ubicacion;
        this.email = email;
    }

    @Override
    public String toString() {

        return "AppAboutMe{" +
                "id=" + id +
                ", imagen='" + imagen + '\'' +
                ", header='" + header + '\'' +
                ", sobremi='" + sobremi + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
