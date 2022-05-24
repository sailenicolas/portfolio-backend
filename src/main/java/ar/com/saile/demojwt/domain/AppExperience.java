package ar.com.saile.demojwt.domain;

import ar.com.saile.demojwt.enums.TipoDeEmpleo;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AppExperience implements Serializable {
    private final static String ID_COLUMN = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COLUMN, nullable = false)
    private Long id;

    private String cargo;
    private String  empresa;
    TipoDeEmpleo tipoDeEmpleo;
    private String imagen ;
    private Date inicio ;
    private String fin;

    public AppExperience(String cargo, String empresa, TipoDeEmpleo tipoDeEmpleo, String imagen, Date inicio, String fin, String ubicacion) {

        this.cargo = cargo;
        this.empresa = empresa;
        this.tipoDeEmpleo = tipoDeEmpleo;
        this.imagen = imagen;
        this.inicio = inicio;
        this.fin = fin;
        this.ubicacion = ubicacion;
    }

    private String ubicacion;
}
