package ro.utm.jc.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.model.data.SingleSerise;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.model.responses.ClientResponse;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.responses.SingleDataSeriseResponse;
import ro.utm.jc.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Clients"})
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "List of clients", response = ClientResponse.class)
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public ClientResponse getClientsByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "contact"   , required = false) String contact,
            Pageable pageable
    ) {
        ClientResponse resp = new ClientResponse();
        Client qry = new Client();
        if (id != null)  { qry.setId(id); }
        if (contact != null) { qry.setName(contact);}

        Page<Client> clientPage = clientService.findAll(qry, pageable);
        resp.setPageStats(clientPage, true);
        resp.setItems(clientPage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a client", response = OperationResponse.class)
    @RequestMapping(value = "/clients/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createClient(@RequestBody Client client, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (clientService.existsByName(client.getName()) ){
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create client - Client already exists ");
        }
        else{
            clientService.save(client);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Client created");
        }
        return resp;
    }

    @ApiOperation(value = "Update client", response = OperationResponse.class)
    @RequestMapping(value = "/clients/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updateClient(@RequestBody Client client, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        clientService.save(client);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Client updated");

        return resp;
    }

    @ApiOperation(value = "Delete a client", response = OperationResponse.class)
    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deleteClient(@PathVariable("clientId") Long clientId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();
        if (clientService.existsById(clientId) ){
            clientService.deleteById(clientId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Client deleted");
        }
        else{
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("No client exists");
        }
        return resp;
    }

    @ApiOperation(value = "Client stats", response = SingleDataSeriseResponse.class)
    @RequestMapping(value = "/client-stats/{type}", method = RequestMethod.GET)
    public SingleDataSeriseResponse getClientStats(@PathVariable("type") String type) {
        String fieldName = "";

        SingleSerise singleSerise;
        SingleDataSeriseResponse resp = new SingleDataSeriseResponse();
        ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();

        if (type.equalsIgnoreCase("availability") || type.equalsIgnoreCase("available_flag")) {
            fieldName = " available_flag ";
        } else if (type.equalsIgnoreCase("environment") || type.equalsIgnoreCase("environment")) {
            fieldName = " environment ";
        } else {
            fieldName = " available_flag ";
        }

        String sql = "select count(*) as value, " + fieldName + " as name from servers group by " + fieldName;
        String countType = new String();
        long count;


        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : list) {
            singleSerise = new SingleSerise(row.get("name").toString().equalsIgnoreCase("1") ? "Available" :
                    row.get("name").toString().equalsIgnoreCase("0") ? "Not available" : row.get("name").toString(), new BigDecimal((long) row.get("value")));
            dataItemList.add(singleSerise);
        }

        resp.setItems(dataItemList);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Servers by " + fieldName);
        //resp.setItems(singleSerise);
        return resp;
    }

}
