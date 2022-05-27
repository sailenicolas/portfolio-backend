package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Proxy(lazy = false)
@JsonIgnoreProperties({"roleCollection", "password",})

public class AppUser implements Serializable {

    private final static String ID_COLUMN = "id";

    public AppUser(String username) {

        this.username = username;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email(message = "Email no valido")
    @NotEmpty
    private String email;

    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 15, message = "Nombre de usuario no cumple requisitos")
    @NotEmpty
    private String username;

    @Column(nullable = false)
    @Size(min = 4, max = 15, message = "Contraseña no cumple requisitos")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<AppRole> roleCollection = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userApp")
    private Collection<AppSoftSkill> softSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userApp")
    private Collection<AppEducation> educations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userApp")
    private Collection<AppExperience> experiences = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userApp")
    private Collection<AppProject> projects = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "appUser")
    private AppAboutMe aboutMe;

    @Override
    public String toString() {

        return "AppUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]" + '\'' +
                ", roleCollection=" + roleCollection +
                ", softSkills=" + softSkills +
                ", educations=" + educations +
                ", experiences=" + experiences +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return getId().equals(appUser.getId()) && getEmail().equals(appUser.getEmail()) && getUsername().equals(appUser.getUsername());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getEmail(), getUsername());
    }
}
