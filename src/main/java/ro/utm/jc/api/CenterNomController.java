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
import ro.utm.jc.model.entities.CenterNomenclature;
import ro.utm.jc.model.responses.CenterNomResponse;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.service.CenterNomService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"CenterNomController"})
public class CenterNomController {

    @Autowired
    private CenterNomService centerNomService;

    @ApiOperation(value = "List of all assigned centers", response = CenterNomResponse.class)
    @RequestMapping(value = "/centers", method = RequestMethod.GET)
    public CenterNomResponse getCentersByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "name"   , required = false) String name,
            Pageable pageable
    ) {
        CenterNomResponse resp = new CenterNomResponse();
        CenterNomenclature qry = new CenterNomenclature();
        if (id != null)  { qry.setId(id); }
        if (name != null) { qry.setName(name);}

        Page<CenterNomenclature> centerNomenclaturePage = centerNomService.findAll(qry, pageable);
        resp.setPageStats(centerNomenclaturePage, true);
        resp.setItems(centerNomenclaturePage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a center in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/centers/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createCenter(@RequestBody CenterNomenclature centerNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (centerNomService.existsByName(centerNomenclature.getName())) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Center already exists ");
        } else {
            centerNomService.save(centerNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Center created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a center in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/centers/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updateCenter(@RequestBody CenterNomenclature centerNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        centerNomService.save(centerNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Center updated");

        return resp;
    }

    @ApiOperation(value = "Delete a center from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/centers/{centerId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deleteCenter(@PathVariable("centerId") Long centerId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            centerNomService.deleteById(centerId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Center deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting center, check logs!");
            log.error("Exception caught deleting center :", e);
        }

        return resp;
    }

}
