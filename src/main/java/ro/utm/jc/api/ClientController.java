package ro.utm.jc.api;

import com.github.javafaker.Faker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.jasperreports.JasperReportsUtils;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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

    private final String SIMPLE_REPORT = "/jasper/report_templates/simpleReport.jrxml";

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
        } else if (type.equalsIgnoreCase("fidelityGroup")) {
            fieldName = " fidelityGroup ";
            sql ="select count(cl.*) as value, fn.group_name as name from clients cl" +
                    " join fidelity_nomenclature fn on cl.fidelity_nom_id = fn.id group by fn.group_name;";
        }

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

        Instant start = Instant.now();

        List<FidelityNomenclature> fidelityNomenclatures = fidelityNomService.findAll();
        List<CountryNomenclature> countryNomenclatures = countryNomService.findAll();
        List<PriceNomenclature> priceNomenclatures = priceNomService.findAll();
        List<PaymentNomenclature> paymentNomenclatures = paymentNomService.findAll();
        List<CenterNomenclature> centerNomenclatures = centerNomService.findAll();
        List<OrgNomenclature> orgNomenclatures = orgNomService.findAll();
        Faker faker = new Faker();

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

        Instant start = Instant.now();

        CompletableFuture<List<FidelityNomenclature>> completableFutureFidelityNom = fidelityNomService.findAllAsync();
        CompletableFuture<List<CountryNomenclature>> completableFutureCountryNom = countryNomService.findAllAsync();
        CompletableFuture<List<PriceNomenclature>> completableFuturePriceNom = priceNomService.findAllAsync();
        CompletableFuture<List<PaymentNomenclature>> completableFuturePaymentNom = paymentNomService.findAllAsync();
        CompletableFuture<List<CenterNomenclature>> completableFutureCenterNom = centerNomService.findAllAsync();
        CompletableFuture<List<OrgNomenclature>> completableFutureOrgTypeNom = orgNomService.findAllAsync();


        // Wait until they are all done
        CompletableFuture.allOf(completableFutureFidelityNom, completableFutureCountryNom, completableFuturePriceNom,
                completableFuturePaymentNom, completableFutureCenterNom, completableFutureOrgTypeNom).join();

        List<Client> clientList = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch countDownLatch = new CountDownLatch(10);

        List<Thread> clientWorkerThreads = Stream
                .generate(() -> {
                    try {
                        return new Thread(new ClientWorker(completableFutureFidelityNom.get(), completableFutureCountryNom.get(),
                                completableFuturePriceNom.get(), completableFuturePaymentNom.get(), completableFutureOrgTypeNom.get(),
                                completableFutureCenterNom.get(), clientList, countDownLatch, (multiThreadedNumberOfRecords/10)));
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("Exception during processing : ", e);
                    }
                    return null;
                })
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

    @ApiOperation(value = "Generate a simple report using a single threaded manner", response = ResponseEntity.class)
    @RequestMapping(value = "/simpleReportGeneration", method = RequestMethod.GET)
    public ResponseEntity<Resource> generateSimplePDF(HttpServletRequest request) throws IOException {
        Instant start = Instant.now();
        File pdfFile = File.createTempFile("test", ".pdf");
        String contentType = null;

        List<FidelityNomenclature> fidelityNomenclatures = fidelityNomService.findAll();
        List<CountryNomenclature> countryNomenclatures = countryNomService.findAll();
        List<PriceNomenclature> priceNomenclatures = priceNomService.findAll();
        List<PaymentNomenclature> paymentNomenclatures = paymentNomService.findAll();
        List<CenterNomenclature> centerNomenclatures = centerNomService.findAll();
        List<OrgNomenclature> orgNomenclatures = orgNomService.findAll();
        List<Client> clients = clientService.findAll();

        try(FileOutputStream pos = new FileOutputStream(pdfFile)) {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate(SIMPLE_REPORT);

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("title", "Simple report generation through a single thread");
            parameters.put("countryData", new JRBeanCollectionDataSource(countryNomenclatures));
            parameters.put("centerData", new JRBeanCollectionDataSource(centerNomenclatures));
            parameters.put("fidelityData", new JRBeanCollectionDataSource(fidelityNomenclatures));
            parameters.put("priceData", new JRBeanCollectionDataSource(priceNomenclatures));
            parameters.put("paymentData", new JRBeanCollectionDataSource(paymentNomenclatures));
            parameters.put("orgTypeData", new JRBeanCollectionDataSource(orgNomenclatures));
            parameters.put("clientData", new JRBeanCollectionDataSource(clients));

            Instant finish = Instant.now();

            parameters.put("timeTaken", "Report generated in : " + getDurationBreakdown(Duration.between(start, finish).toMillis()));

            JasperReportsUtils.renderAsPdf(report, parameters, new JREmptyDataSource(), pos);

            contentType = request.getServletContext().getMimeType(pdfFile.getAbsolutePath());

        } catch (final Exception e) {
            System.out.println(String.format("An error occured during PDF creation: %s", e));
            e.printStackTrace();
        }

        Path filePath = Paths.get(pdfFile.getAbsolutePath());
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "Generate a simple report using a multi threaded manner", response = ResponseEntity.class)
    @RequestMapping(value = "/simpleReportGenerationASync", method = RequestMethod.GET)
    public ResponseEntity<Resource> generateSimplePDFASync(HttpServletRequest request) throws IOException {
        Instant start = Instant.now();
        File pdfFile = File.createTempFile("test", ".pdf");
        String contentType = null;

        CompletableFuture<List<FidelityNomenclature>> completableFutureFidelityNom = fidelityNomService.findAllAsync();
        CompletableFuture<List<CountryNomenclature>> completableFutureCountryNom = countryNomService.findAllAsync();
        CompletableFuture<List<PriceNomenclature>> completableFuturePriceNom = priceNomService.findAllAsync();
        CompletableFuture<List<PaymentNomenclature>> completableFuturePaymentNom = paymentNomService.findAllAsync();
        CompletableFuture<List<CenterNomenclature>> completableFutureCenterNom = centerNomService.findAllAsync();
        CompletableFuture<List<OrgNomenclature>> completableFutureOrgTypeNom = orgNomService.findAllAsync();
        CompletableFuture<List<Client>> completableFutureClients = clientService.findAllAsync();

        CompletableFuture.allOf(completableFutureFidelityNom, completableFutureCountryNom, completableFuturePriceNom,
                completableFuturePaymentNom, completableFutureCenterNom, completableFutureOrgTypeNom, completableFutureClients).join();

        try(FileOutputStream pos = new FileOutputStream(pdfFile)) {
            // Load the invoice jrxml template.
            final JasperReport report = loadTemplate(SIMPLE_REPORT);

            Map<String, Object> parameters = new HashMap<String, Object>();

            parameters.put("title", "Simple report generation through a single thread");
            parameters.put("countryData", new JRBeanCollectionDataSource(completableFutureCountryNom.get()));
            parameters.put("centerData", new JRBeanCollectionDataSource(completableFutureCenterNom.get()));
            parameters.put("fidelityData", new JRBeanCollectionDataSource(completableFutureFidelityNom.get()));
            parameters.put("priceData", new JRBeanCollectionDataSource(completableFuturePriceNom.get()));
            parameters.put("paymentData", new JRBeanCollectionDataSource(completableFuturePaymentNom.get()));
            parameters.put("orgTypeData", new JRBeanCollectionDataSource(completableFutureOrgTypeNom.get()));
            parameters.put("clientData", new JRBeanCollectionDataSource(completableFutureClients.get()));

            Instant finish = Instant.now();

            parameters.put("timeTaken", "Report generated in : " + getDurationBreakdown(Duration.between(start, finish).toMillis()));

            JasperReportsUtils.renderAsPdf(report, parameters, new JREmptyDataSource(), pos);

            contentType = request.getServletContext().getMimeType(pdfFile.getAbsolutePath());

        } catch (final Exception e) {
            System.out.println(String.format("An error occured during PDF creation: %s", e));
            e.printStackTrace();
        }

        Path filePath = Paths.get(pdfFile.getAbsolutePath());
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private JasperReport loadTemplate(String reportTemplate) throws JRException {

        log.info(String.format("Jasper report template path : %s", SIMPLE_REPORT));

        final InputStream reportInputStream = getClass().getResourceAsStream(SIMPLE_REPORT);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
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
