package ar.com.saile.demojwt.domain;

import ar.com.saile.demojwt.enums.TipoDeEmpleo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.LocalDate;

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

    @NotNull
    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    @NotNull
    private String empresa;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Enumerated(EnumType.ORDINAL)
    TipoDeEmpleo tipoDeEmpleo;

    @Nullable
    private String imagen;

    @NotNull
    @Column(nullable = false)
    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate inicio;

    @Nullable
    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate fin;

    @Nullable
    private String ubicacion;

    @ManyToOne()
    @JsonBackReference
    private AppUser userApp;

    public AppExperience(String cargo, String empresa, TipoDeEmpleo tipoDeEmpleo, String imagen, @PastOrPresent LocalDate inicio, @PastOrPresent LocalDate fin, String ubicacion) {

        this.cargo = cargo;
        this.empresa = empresa;
        this.tipoDeEmpleo = tipoDeEmpleo;
        this.imagen = imagen;
        this.inicio = inicio;
        this.fin = fin;
        this.ubicacion = ubicacion;
    }

}
