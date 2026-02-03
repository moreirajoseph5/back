package ec.prueba.back.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class CommerceValidationRepository {

    @PersistenceContext
    private EntityManager em;

    public int validate(LocalDate processDate) {
        StoredProcedureQuery sp =
                em.createStoredProcedureQuery("sp_validate_commerce");

        sp.registerStoredProcedureParameter(1, LocalDate.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

        sp.setParameter(1, processDate);
        sp.execute();

        return (Integer) sp.getOutputParameterValue(2);
    }
}