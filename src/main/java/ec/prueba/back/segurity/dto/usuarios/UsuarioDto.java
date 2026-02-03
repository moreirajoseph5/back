package ec.prueba.back.segurity.dto.usuarios;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UsuarioDto implements Serializable {
    private Long id;
    private String tipoIdentificacion;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String email;
    private String username;
    private String password;
    private String telefono;
    private String direccion;
    private String sexo;
    private Boolean activo;
    private Boolean esPrimerInicio;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private List<RolDto> roles;
}
