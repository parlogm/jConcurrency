package ro.utm.jc.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.model.entities.OrgNomenclature;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.responses.OrgNomResponse;
import ro.utm.jc.service.OrgNomService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"OrgNomController"})
public class OrgNomController {

    @Autowired
    private OrgNomService orgNomService;

    @ApiOperation(value = "List of all organizational types", response = OrgNomResponse.class)
    @RequestMapping(value = "/orgTypes", method = RequestMethod.GET)
    public OrgNomResponse getOrgTypesByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "type"   , required = false) String type,
            Pageable pageable
    ) {
        OrgNomResponse resp = new OrgNomResponse();
        OrgNomenclature qry = new OrgNomenclature();
        if (id != null)  { qry.setId(id); }
        if (type != null) { qry.setType(type);}

        Page<OrgNomenclature> orgNomenclaturePage = orgNomService.findAll(qry, pageable);
        resp.setPageStats(orgNomenclaturePage, true);
        resp.setItems(orgNomenclaturePage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a org type in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/orgTypes/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createOrgType(@RequestBody OrgNomenclature orgNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (orgNomService.existsByType(orgNomenclature.getType())) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Org type already exists ");
        } else {
            orgNomService.save(orgNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Org type created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a org type in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/orgTypes/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updateOrgType(@RequestBody OrgNomenclature orgNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        orgNomService.save(orgNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Org type updated");

        return resp;
    }

    @ApiOperation(value = "Delete a org type from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/orgTypes/{orgTypeId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deleteOrgType(@PathVariable("orgTypeId") Long orgTypeId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            orgNomService.deleteById(orgTypeId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Org type deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting org type, check logs!");
            log.error("Exception caught deleting org type :", e);
        }

        return resp;
    }

}
