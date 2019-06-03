package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ClientsGenerationResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private String numbersGenerated;

    @ApiModelProperty(required = true, value = "")
    private String elapsedTime;

}
