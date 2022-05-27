package ar.com.saile.demojwt.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class AppAboutMe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    private String header;

    private String aboutMe;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private AppUser appUser;

    public AppAboutMe(String imagen, String header, String aboutMe, String name) {

        this.imagen = imagen;
        this.header = header;
        this.aboutMe = aboutMe;
        this.name = name;
    }
}
