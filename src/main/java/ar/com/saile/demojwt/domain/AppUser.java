package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@Transactional(rollbackFor = Exception.class)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"roleCollection", "password",})
@Proxy(lazy = false)
public class AppUser implements Serializable {

    private final static String ID_COLUMN = "id";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
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
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return getId().equals(appUser.getId()) && getUsername().equals(appUser.getUsername());
    }

    @Override
    public String toString() {

        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]" + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUsername());
    }

    public AppUser(String username) {

        this.username = username;
    }

}
