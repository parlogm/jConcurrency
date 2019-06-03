package ro.utm.jc.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "payment_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_ID_SEQ")
    @SequenceGenerator(name = "PAYMENT_ID_SEQ", sequenceName = "PAYMENT_ID_SEQ")
    private Long id;

    private String methodName;

}
