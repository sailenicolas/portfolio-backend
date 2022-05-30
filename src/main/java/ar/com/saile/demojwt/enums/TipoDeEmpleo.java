package ar.com.saile.demojwt.enums;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TipoDeEmpleo {
    @JsonEnumDefaultValue
    UNKNOWN("UNKNOWN"),

    JORNADAPARCIAL("jornadaParcial"),
    JORNADACOMPLETA("jornadaCompleta"),
    AUTONOMO("autonomo"),
    INDEPENDIENTE("independiente"),
    TEMPORAL("temporal"),
    PRACTICAS("practicas"),
    FORMACION("formacion"),
    SEASONAL("seasonal");

    TipoDeEmpleo(String n) {

    }
}
