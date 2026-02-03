package ec.prueba.back.segurity.dto.usuarios;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolDto implements Serializable {
    private Integer id;
    private String nombre;
}
