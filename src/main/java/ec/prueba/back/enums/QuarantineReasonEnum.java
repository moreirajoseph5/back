package ec.prueba.back.enums;

public enum QuarantineReasonEnum {

    NOMCOMRED_EMPTY("El nombre del comercio (nomcomred) se encuentra vacío"),
    NUMDOC_INVALID("El número (numdoc) contiene letras o caracteres especiales");

    private final String descripcion;

    QuarantineReasonEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
