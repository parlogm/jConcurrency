package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.entities.CenterNomenclature;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CenterNomResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<CenterNomenclature> items;

}
