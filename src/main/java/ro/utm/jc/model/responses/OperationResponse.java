package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data //for getters and setters
public class OperationResponse {
    public enum ResponseStatusEnum {SUCCESS, ERROR, WARNING, NO_ACCESS};
    @ApiModelProperty(required = true)
    private ResponseStatusEnum  operationStatus;
    private String  operationMessage;
}