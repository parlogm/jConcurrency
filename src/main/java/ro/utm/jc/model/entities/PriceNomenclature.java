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
@Table(name = "price_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class PriceNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRICE_ID_SEQ")
    @SequenceGenerator(name = "PRICE_ID_SEQ", sequenceName = "PRICE_ID_SEQ")
    private Long id;

    private String groupName;

}
