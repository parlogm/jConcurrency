package ro.utm.jc.model.server;

import ro.utm.jc.model.responses.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.responses.PageResponse;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServerResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Server> items;

}