package ec.prueba.back.mapper;

import ec.prueba.back.dto.CommerceCsvDto;

public class CommerceCsvMapper {

    public static Object[] toSpParams(CommerceCsvDto dto) {
        return new Object[]{
                dto.processDate(),
                dto.nomComRed(),
                dto.numDoc(),
                dto.amount()
        };
    }
}