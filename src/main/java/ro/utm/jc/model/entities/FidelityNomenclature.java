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
@Table(name = "fidelity_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class FidelityNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIDNOM_ID_SEQ")
    @SequenceGenerator(name = "FIDNOM_ID_SEQ", sequenceName = "FIDNOM_ID_SEQ")
    private Long id;

    private String groupName;

}
