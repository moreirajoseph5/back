package ec.prueba.back.segurity.exception;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String error;
    private Map<String, ?> message;
}
