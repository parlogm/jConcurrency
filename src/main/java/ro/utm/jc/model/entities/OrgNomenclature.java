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
@Table(name = "org_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class OrgNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_ID_SEQ")
    @SequenceGenerator(name = "ORG_ID_SEQ", sequenceName = "ORG_ID_SEQ")
    private Long id;

    private String type;

}
