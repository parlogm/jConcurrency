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
import ro.utm.jc.model.entities.PaymentNomenclature;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.responses.PaymentNomResponse;
import ro.utm.jc.service.PaymentNomService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"PaymentNomController"})
public class PaymentNomController {

    @Autowired
    private PaymentNomService paymentNomService;

    @ApiOperation(value = "List of all payment methods", response = PaymentNomResponse.class)
    @RequestMapping(value = "/paymentMethods", method = RequestMethod.GET)
    public PaymentNomResponse getPaymentMethodsByPage(
            @ApiParam(value = ""    )               @RequestParam(value = "page"  ,  defaultValue="0"   ,  required = false) Integer page,
            @ApiParam(value = "between 1 to 1000" ) @RequestParam(value = "size"  ,  defaultValue="20"  ,  required = false) Integer size,
            @RequestParam(value = "id"  , required = false) Long id,
            @RequestParam(value = "methodName"   , required = false) String methodName,
            Pageable pageable
    ) {
        PaymentNomResponse resp = new PaymentNomResponse();
        PaymentNomenclature qry = new PaymentNomenclature();
        if (id != null)  { qry.setId(id); }
        if (methodName != null) { qry.setMethodName(methodName);}

        Page<PaymentNomenclature> paymentNomenclaturePage = paymentNomService.findAll(qry, pageable);
        resp.setPageStats(paymentNomenclaturePage, true);
        resp.setItems(paymentNomenclaturePage.getContent());
        return resp;
    }

    @ApiOperation(value = "Add a payment method in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/paymentMethods/create", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse createPaymentMethod(@RequestBody PaymentNomenclature paymentNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        if (paymentNomService.existsByMethodName(paymentNomenclature.getMethodName())) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable create nomenclature entry - Payment method already exists ");
        } else {
            paymentNomService.save(paymentNomenclature);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Payment method created");
        }
        return resp;
    }

    @ApiOperation(value = "Update a payment method in the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/paymentMethods/update", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse updatePaymentMethod(@RequestBody PaymentNomenclature paymentNomenclature, HttpServletRequest req) {

        OperationResponse resp = new OperationResponse();

        paymentNomService.save(paymentNomenclature);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Payment method updated");

        return resp;
    }

    @ApiOperation(value = "Delete a payment method from the nomenclature", response = OperationResponse.class)
    @RequestMapping(value = "/paymentMethods/{pmId}", method = RequestMethod.DELETE, produces = {"application/json"})
    public OperationResponse deletePaymentMethod(@PathVariable("pmId") Long pmId, HttpServletRequest req) {
        OperationResponse resp = new OperationResponse();

        try {
            paymentNomService.deleteById(pmId);
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Payment method deleted");
        } catch (Exception e) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Exception caught deleting payment method, check logs!");
            log.error("Exception caught deleting payment method :", e);
        }

        return resp;
    }

}
