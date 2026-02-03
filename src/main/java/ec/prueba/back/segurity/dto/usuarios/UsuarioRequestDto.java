package ec.prueba.back.segurity.dto.usuarios;


import lombok.Data;

import java.util.Date;

@Data
public class UsuarioRequestDto {
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
    private Integer rolId;
}
