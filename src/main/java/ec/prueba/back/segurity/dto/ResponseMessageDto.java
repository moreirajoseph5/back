package ec.prueba.back.segurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseMessageDto implements Serializable {
    private String message;
}
