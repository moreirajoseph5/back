package ec.prueba.back.repository;

import ec.prueba.back.entity.CommerceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CommerceRepository extends JpaRepository<CommerceEntity, Long> {

    @Procedure(name = "Commerce.create")
    void create(
            String p_processdate,
            String p_nomcomred,
            String p_numdoc,
            BigDecimal p_amount
    );
}