package ec.prueba.back.segurity.dto;

import ec.prueba.back.segurity.dto.usuarios.RolDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TokenDto implements Serializable {
    private String access_token;
    private String token_type;
    private String expires_in;
    private Long exp;
    private List<RolDto> rol;
  //  private String refresh_token;
}
