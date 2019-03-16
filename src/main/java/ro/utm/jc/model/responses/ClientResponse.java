package ro.utm.jc.model.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.model.responses.PageResponse;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ClientResponse extends PageResponse {

    @ApiModelProperty(required = true, value = "")
    private List<Client> items;

}
