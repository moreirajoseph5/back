package ec.prueba.back.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commerce_quarantine")
@Data
public class CommerceQuarantineEntity {

    @Id
    @Column(name = "pc_numdoc")
    private String pcNumdoc;

    private LocalDate pcProcessdate;
    private String pcNomcomred;
    private BigDecimal pcAmount;
    private String motivo;

}
