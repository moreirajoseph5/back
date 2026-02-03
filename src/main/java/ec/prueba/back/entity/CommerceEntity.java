package ec.prueba.back.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commerce")
@NamedStoredProcedureQuery(
        name = "Commerce.create",
        procedureName = "sp_create_commerce",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_processdate", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nomcomred", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_numdoc", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_amount", type = BigDecimal.class)
        }
)
@Data
public class CommerceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate pcProcessdate;
    private String pcNomcomred;
    private String pcNumdoc;
    private BigDecimal pcAmount;
}
