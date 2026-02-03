package ec.prueba.back.segurity.dto.usuarios;

import lombok.Data;

@Data
public class NewPasswordDto {
    private Integer idUsuario;
    private String newPassword;
}
