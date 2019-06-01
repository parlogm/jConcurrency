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
import ro.utm.jc.model.entities.PriceNomenclature;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.responses.PriceNomResponse;
import ro.utm.jc.service.PriceNomService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"PriceNomController"})
public class PriceNomController {

    @Autowired
    private PriceNomService priceNomService;

    @ApiOperation(value = "List of all price groups", response = PriceNomResponse.class)
    @RequestMapping(value = "/prices", method = RequestMethod.GET)
    public PriceNomResponse getPricesByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "groupName"   , required = false) String groupName,
            Pageable pageable
    ) {
        PriceNomResponse resp = new PriceNomResponse();
        PriceNomenclature qry = new PriceNomenclature();
        if (id != null)  { qry.setId(id); }
        if (groupName != null) { qry.setGroupName(groupName);}

        Page<PriceNomenclature> priceNomenclaturePage = priceNomService.findAll(qry, pageable);
        resp.setPageStats(priceNomenclaturePage, true);
        resp.setItems(priceNomenclaturePage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a price group in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/prices/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createPriceGroup(@RequestBody PriceNomenclature priceNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (priceNomService.existsByGroupName(priceNomenclature.getGroupName()) ){
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Price group already exists ");
        } else {
            priceNomService.save(priceNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Price group created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a price group in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/prices/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updatePriceGroup(@RequestBody PriceNomenclature priceNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        priceNomService.save(priceNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Price group updated");

        return resp;
    }

    @ApiOperation(value = "Delete a price group from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/prices/{pgId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deletePriceGroup(@PathVariable("pgId") Long pgId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            priceNomService.deleteById(pgId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Price group deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting price group, check logs!");
            log.error("Exception caught deleting price group :", e);
        }

        return resp;
    }

}
