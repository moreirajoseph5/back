package ec.prueba.back.utils;

import ec.prueba.back.dto.CommerceCsvDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class CsvUtils {

    public static List<CommerceCsvDto> read(MultipartFile file) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream()))) {

            return reader.lines()
                    .skip(1) // header
                    .map(line -> line.split(","))
                    .map(cols -> new CommerceCsvDto(
                            cols[0].trim(),                      // pc_nomcomred
                            cols[1].trim(),                      // pc_numdoc
                            LocalDate.parse(cols[2].trim()),     // pc_processdate
                            new BigDecimal(cols[3].trim())       // amount
                    ))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error leyendo CSV", e);
        }
    }
}
