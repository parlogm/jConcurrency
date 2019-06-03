package ro.utm.jc.api;

import com.github.javafaker.Faker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.async.ClientWorker;
import ro.utm.jc.model.data.SingleSerise;
import ro.utm.jc.model.entities.*;
import ro.utm.jc.model.responses.ClientResponse;
import ro.utm.jc.model.responses.ClientsGenerationResponse;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.responses.SingleDataSeriseResponse;
import ro.utm.jc.service.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = {"Clients"})
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FidelityNomService fidelityNomService;

    @Autowired
    private CountryNomService countryNomService;

    @Autowired
    private PriceNomService priceNomService;

    @Autowired
    private PaymentNomService paymentNomService;

    @Autowired
    private OrgNomService orgNomService;

    @Autowired
    private CenterNomService centerNomService;

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
        String sql = "";

        SingleSerise singleSerise;
        SingleDataSeriseResponse resp = new SingleDataSeriseResponse();
        ArrayList<SingleSerise> dataItemList = new ArrayList<SingleSerise>();

        if (type.equalsIgnoreCase("orgType")) {
            fieldName = " orgType ";
            sql = "select count(cl.*) as value, o.type as name from clients cl" +
                    " join org_nomenclature o on cl.org_nom_id = o.id group by o.type;";
        } else if (type.equalsIgnoreCase("country") ) {
            fieldName = " country ";
            sql = "select count(cl.*) as value, cn.country as name from clients cl" +
                    " join country_nomenclature cn on cl.country_id = cn.id group by cn.country;";
        } else {
            fieldName = " available_flag ";
        }

         //= "select count(cl.*) as value, " + fieldName + " as name from clients cl group by " + fieldName;

        /*if () {

        }*/

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : list) {
            singleSerise = new SingleSerise(row.get("name").toString(), new BigDecimal((long) row.get("value")));
            dataItemList.add(singleSerise);
        }

        resp.setItems(dataItemList);
        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("Clients by " + fieldName);
        //resp.setItems(singleSerise);
        return resp;
    }

    @ApiOperation(value = "Generate a number of clients iteratively", response = ClientsGenerationResponse.class)
    @RequestMapping(value = "/clients/iterativeGeneration", method = RequestMethod.GET)
    public ClientsGenerationResponse iteratigeGeneration(@RequestParam(value = "iterativeNumberOfRecords") Integer iterativeNumberOfRecords,
                                                         @RequestParam(value = "iterativeSaveToDatabase") Boolean iterativeSaveToDatabase) {
        ClientsGenerationResponse resp = new ClientsGenerationResponse();

        List<FidelityNomenclature> fidelityNomenclatures = fidelityNomService.findAll();
        List<CountryNomenclature> countryNomenclatures = countryNomService.findAll();
        List<PriceNomenclature> priceNomenclatures = priceNomService.findAll();
        List<PaymentNomenclature> paymentNomenclatures = paymentNomService.findAll();
        List<CenterNomenclature> centerNomenclatures = centerNomService.findAll();
        List<OrgNomenclature> orgNomenclatures = orgNomService.findAll();
        Faker faker = new Faker();

        Instant start = Instant.now();

        List<Client> clientList = new ArrayList<>();

        IntStream.range(0, iterativeNumberOfRecords).forEach(
                i -> {
                    clientList.add(
                            clientService.buildClient(fidelityNomenclatures, countryNomenclatures, priceNomenclatures,
                                    paymentNomenclatures, orgNomenclatures, centerNomenclatures, faker)
                    );
                }
        );

        if (iterativeSaveToDatabase) {
            clientService.saveAll(clientList);
        }

        Instant finish = Instant.now();

        resp.setElapsedTime(getDurationBreakdown(Duration.between(start, finish).toMillis()));
        resp.setNumbersGenerated(String.valueOf(clientList.size()));

        return resp;
    }

    @ApiOperation(value = "Generate a number of clients in a multithreaded way", response = ClientsGenerationResponse.class)
    @RequestMapping(value = "/clients/multiThreadGeneration", method = RequestMethod.GET)
    public ClientsGenerationResponse multiThreadGeneration(@RequestParam(value = "multiThreadedNumberOfRecords") Integer multiThreadedNumberOfRecords,
                                                         @RequestParam(value = "multiThreadedSaveToDatabase") Boolean multiThreadedSaveToDatabase) throws InterruptedException {
        ClientsGenerationResponse resp = new ClientsGenerationResponse();

        List<FidelityNomenclature> fidelityNomenclatures = fidelityNomService.findAll();
        List<CountryNomenclature> countryNomenclatures = countryNomService.findAll();
        List<PriceNomenclature> priceNomenclatures = priceNomService.findAll();
        List<PaymentNomenclature> paymentNomenclatures = paymentNomService.findAll();
        List<CenterNomenclature> centerNomenclatures = centerNomService.findAll();
        List<OrgNomenclature> orgNomenclatures = orgNomService.findAll();

        Instant start = Instant.now();

        List<Client> clientList = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<Thread> clientWorkerThreads = Stream
                .generate(() -> new Thread(new ClientWorker(fidelityNomenclatures, countryNomenclatures, priceNomenclatures,
                        paymentNomenclatures, orgNomenclatures, centerNomenclatures, clientList, countDownLatch,
                        (multiThreadedNumberOfRecords/10))))
                .limit(10)
                .collect(toList());

        clientWorkerThreads.forEach(Thread::start);
        countDownLatch.await();
        System.out.println("Latch released");

        if (multiThreadedSaveToDatabase) {
            clientService.saveAll(clientList);
        }

        Instant finish = Instant.now();

        resp.setElapsedTime(getDurationBreakdown(Duration.between(start, finish).toMillis()));
        resp.setNumbersGenerated(String.valueOf(clientList.size()));

        return resp;
    }

    private static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        /*long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;*/
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        long milliseconds = millis % 1000;

        /*return String.format("%d Days %d Hours %d Minutes %d Seconds %d Milliseconds",
                days, hours, minutes, seconds, milliseconds);*/
        return String.format("%d m %d s %d ms",
                minutes, seconds, milliseconds);
    }

}
