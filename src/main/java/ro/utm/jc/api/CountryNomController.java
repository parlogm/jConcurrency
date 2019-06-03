package ro.utm.jc.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.model.entities.CountryNomenclature;
import ro.utm.jc.model.responses.CountryNomResponse;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.service.CountryNomService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"CountryNomenclature"})
public class CountryNomController {

    @Autowired
    private CountryNomService countryNomService;

    @ApiOperation(value = "List of all countries", response = CountryNomResponse.class)
    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public CountryNomResponse getCountriesByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "country"   , required = false) String country,
            Pageable pageable
    ) {
        CountryNomResponse resp = new CountryNomResponse();
        CountryNomenclature qry = new CountryNomenclature();
        if (id != null)  { qry.setId(id); }
        if (country != null) { qry.setCountry(country);}

        Page<CountryNomenclature> countryNomenclaturePage = countryNomService.findAll(qry, pageable);
        resp.setPageStats(countryNomenclaturePage, true);
        resp.setItems(countryNomenclaturePage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a country in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/countries/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createCountry(@RequestBody CountryNomenclature countryNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (countryNomService.existsByCountryName(countryNomenclature.getCountry()) ){
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Country already exists ");
        }
        else{
            countryNomService.save(countryNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Country created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a country in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/countries/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updateCountry(@RequestBody CountryNomenclature countryNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        countryNomService.save(countryNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Country updated");

        return resp;
    }

    @ApiOperation(value = "Delete a country from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/countries/{countryId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deleteCountry(@PathVariable("countryId") Long countryId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            countryNomService.deleteById(countryId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Country deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting country, check logs!");
        }

        return resp;
    }

}
