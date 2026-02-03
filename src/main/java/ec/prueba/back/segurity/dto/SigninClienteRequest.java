package ec.prueba.back.segurity.dto;

import ec.prueba.back.enums.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninClienteRequest {
    private TipoDocumento tipoDocumento;
    private String identificacion;
}
