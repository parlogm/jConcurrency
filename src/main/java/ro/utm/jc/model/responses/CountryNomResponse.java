package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.entities.CountryNomenclature;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CountryNomResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<CountryNomenclature> items;

}
