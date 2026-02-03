package ec.prueba.back.service.declare;

import ec.prueba.back.entity.CommerceQuarantineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface CommerceService {

    void processCsv(MultipartFile file);

    int validate(LocalDate date);

    Page<CommerceQuarantineEntity> listQuarantine(Pageable pageable);
}
