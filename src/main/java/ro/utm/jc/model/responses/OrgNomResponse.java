package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.entities.OrgNomenclature;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrgNomResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<OrgNomenclature> items;

}
