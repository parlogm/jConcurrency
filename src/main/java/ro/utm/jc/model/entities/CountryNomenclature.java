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
@Table(name = "country_nomenclature")
@NoArgsConstructor
@AllArgsConstructor
public class CountryNomenclature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_ID_SEQ")
    @SequenceGenerator(name = "COUNTRY_ID_SEQ", sequenceName = "COUNTRY_ID_SEQ")
    private Long id;

    private String country;
    private String code;

}
