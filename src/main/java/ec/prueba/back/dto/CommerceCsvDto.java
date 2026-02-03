package ec.prueba.back.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CommerceCsvDto(
        String nomComRed,
        String numDoc,
        LocalDate processDate,
        BigDecimal amount
) {}