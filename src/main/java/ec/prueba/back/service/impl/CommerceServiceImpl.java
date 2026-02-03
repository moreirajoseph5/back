package ec.prueba.back.service.impl;

import ec.prueba.back.dto.CommerceCsvDto;
import ec.prueba.back.entity.CommerceQuarantineEntity;
import ec.prueba.back.repository.CommerceQuarantineRepository;
import ec.prueba.back.repository.CommerceRepository;
import ec.prueba.back.repository.CommerceValidationRepository;
import ec.prueba.back.service.declare.CommerceService;
import ec.prueba.back.utils.CsvUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class CommerceServiceImpl implements CommerceService {

    private final CommerceRepository commerceRepo;
    private final CommerceValidationRepository validationRepo;
    private final CommerceQuarantineRepository quarantineRepo;

    public CommerceServiceImpl(
            CommerceRepository commerceRepo,
            CommerceValidationRepository validationRepo,
            CommerceQuarantineRepository quarantineRepo) {
        this.commerceRepo = commerceRepo;
        this.validationRepo = validationRepo;
        this.quarantineRepo = quarantineRepo;
    }

    /**
     * Carga el archivo CSV y registra los datos en la tabla commerce
     */
    @Override
    @Transactional
    public void processCsv(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo CSV está vacío");
        }

        try {
            var records = CsvUtils.read(file);

            for (CommerceCsvDto dto : records) {
                commerceRepo.create(
                        String.valueOf(dto.processDate()),   // LocalDate
                        dto.nomComRed(),     // String
                        dto.numDoc(),        // String
                        dto.amount()         // BigDecimal
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error procesando el archivo CSV", e);
        }
    }



    /**
     * Ejecuta validaciones y mueve registros inválidos a cuarentena
     */
    @Override
    @Transactional
    public int validate(LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException("La fecha de proceso es obligatoria");
        }

        return validationRepo.validate(date);
    }

    /**
     * Lista registros en cuarentena de forma paginada
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommerceQuarantineEntity> listQuarantine(Pageable pageable) {
        return quarantineRepo.findAll(pageable);
    }
}
