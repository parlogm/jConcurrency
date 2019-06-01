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
@Table(name = "center_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class CenterNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CENTER_ID_SEQ")
    @SequenceGenerator(name = "CENTER_ID_SEQ", sequenceName = "CENTER_ID_SEQ")
    private Long id;

    private String name;

}
