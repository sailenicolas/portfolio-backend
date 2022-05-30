package ar.com.saile.demojwt.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AppRole implements Serializable {

    private final static String ID_COLUMN = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AppRole)) return false;
        AppRole appRole = (AppRole) o;
        return getId().equals(appRole.getId()) && getName().equals(appRole.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }

    private String name;

}
