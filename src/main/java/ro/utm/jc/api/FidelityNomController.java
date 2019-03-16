package ro.utm.jc.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.model.entities.FidelityNomenclature;
import ro.utm.jc.model.responses.FidelityNomResponse;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.service.FidelityNomService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"FidelityNomenclateur"})
public class FidelityNomController {

    @Autowired
    private FidelityNomService fidelityNomService;

    @ApiOperation(value = "List of fidelity groups", response = FidelityNomResponse.class)
    @RequestMapping(value = "/fgroups", method = RequestMethod.GET)
    public FidelityNomResponse getFidelityGroupsByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "groupName"   , required = false) String groupName,
            Pageable pageable
    ) {
        FidelityNomResponse resp = new FidelityNomResponse();
        FidelityNomenclature qry = new FidelityNomenclature();
        if (id != null)  { qry.setId(id); }
        if (groupName != null) { qry.setGroupName(groupName);}

        Page<FidelityNomenclature> fidelityPage = fidelityNomService.findAll(qry, pageable);
        resp.setPageStats(fidelityPage, true);
        resp.setItems(fidelityPage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a fidelity group in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/fgroups/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createFidelityGroup(@RequestBody FidelityNomenclature fidelityNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (fidelityNomService.existsByGroupName(fidelityNomenclature.getGroupName()) ){
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Fidelity group already exists ");
        }
        else{
            fidelityNomService.save(fidelityNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Fidelity group created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a fidelity group in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/fgroups/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updateFidelityGroup(@RequestBody FidelityNomenclature fidelityNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        fidelityNomService.save(fidelityNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Fidelity group updated");

        return resp;
    }

    @ApiOperation(value = "Delete a fidelity group from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/fgroups/{fGroupId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deleteClient(@PathVariable("fGroupId") Long fGroupId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            fidelityNomService.deleteById(fGroupId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Fidelity group deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting fidelity group");
        }

        return resp;
    }

}
