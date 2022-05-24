package ar.com.saile.demojwt.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppUser implements Serializable {

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return getId().equals(appUser.getId()) && getEmail().equals(appUser.getEmail()) && getUsername().equals(appUser.getUsername());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getEmail(), getUsername());
    }

    private final static String ID_COLUMN = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String email;

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roleCollection = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AppSoftSkill> softSkills = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AppEducation> educations = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AppExperience> experiences = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<AppProject> projects = new ArrayList<>();
}
