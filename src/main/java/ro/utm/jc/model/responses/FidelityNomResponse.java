package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.entities.FidelityNomenclature;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FidelityNomResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<FidelityNomenclature> items;

}
