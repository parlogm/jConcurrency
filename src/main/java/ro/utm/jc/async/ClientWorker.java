package ro.utm.jc.async;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import ro.utm.jc.model.entities.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class ClientWorker implements Runnable {

    private List<FidelityNomenclature> fidelityNomenclatures;
    private List<CountryNomenclature> countryNomenclatures;
    private List<PriceNomenclature> priceNomenclatures;
    private List<PaymentNomenclature> paymentNomenclatures;
    private List<OrgNomenclature> orgNomenclatures;
    private List<CenterNomenclature> centerNomenclatures;
    private CountDownLatch countDownLatch;
    private List<Client> clientList;
    private Integer numberOfRecords;

    public ClientWorker (List<FidelityNomenclature> fidelityNomenclatures, List<CountryNomenclature> countryNomenclatures,
            List<PriceNomenclature> priceNomenclatures, List<PaymentNomenclature> paymentNomenclatures,
            List<OrgNomenclature> orgNomenclatures, List<CenterNomenclature> centerNomenclatures,
            List<Client> clientList, CountDownLatch countDownLatch, Integer numberOfRecords) {
        this.fidelityNomenclatures = fidelityNomenclatures;
        this.countryNomenclatures = countryNomenclatures;
        this.priceNomenclatures = priceNomenclatures;
        this.paymentNomenclatures = paymentNomenclatures;
        this.orgNomenclatures = orgNomenclatures;
        this.centerNomenclatures = centerNomenclatures;
        this.clientList = clientList;
        this.countDownLatch = countDownLatch;
        this.numberOfRecords = numberOfRecords;
    }

    @Override
    public void run () {

        Faker faker = new Faker();

        log.info("Started to generate clients in thread " + Thread.currentThread().getName());

        IntStream.range(0, numberOfRecords).forEach(
                i -> {
                    clientList.add(
                            Client.builder()
                                    .isCorporate(faker.bool().bool())
                                    .name(faker.company().name())
                                    .createdAt(new Timestamp(new Date().getTime()))
                                    .updatedAt(new Timestamp(new Date().getTime()))
                                    .priceGroup(priceNomenclatures.get(faker.random().nextInt(0, priceNomenclatures.size()-1)))
                                    .salesAgentId(faker.number().randomNumber())
                                    .fidelityNomenclature(fidelityNomenclatures.get(faker.random().nextInt(0, fidelityNomenclatures.size()-1)))
                                    .lastBillingDate(new Date())
                                    .billingRateIndex(Float.valueOf(faker.number().randomDigitNotZero()))
                                    .emailConfirmation(faker.bool().bool())
                                    .daysFromLastBill(0)
                                    .email(faker.internet().emailAddress())
                                    .assignedCenter(centerNomenclatures.get(faker.random().nextInt(0, centerNomenclatures.size()-1)))
                                    .countryNomenclature(countryNomenclatures.get(faker.random().nextInt(0, countryNomenclatures.size()-1)))
                                    .address(faker.address().fullAddress())
                                    .contactPhone(faker.phoneNumber().cellPhone())
                                    .contact(faker.name().fullName())
                                    .paymentMethod(paymentNomenclatures.get(faker.random().nextInt(0, paymentNomenclatures.size()-1)))
                                    .paymentDueIn(0)
                                    .paymentNotification(0)
                                    .contractNr(faker.number().randomNumber())
                                    .contractExpirationDate(faker.date().future(10, TimeUnit.DAYS))
                                    .regCode(faker.code().isbnRegistrant())
                                    .isVatApplicable(faker.bool().bool())
                                    .balance(Float.valueOf(faker.number().numberBetween(100, 1000)))
                                    .debit(Float.valueOf(faker.number().randomDigit()))
                                    .credit(Float.valueOf(faker.number().randomDigit()))
                                    .creditApprovedLimit(Float.valueOf(faker.number().randomDigit()))
                                    .creditLimit(Float.valueOf(faker.number().randomNumber()))
                                    .legalNoticeReceivedOn(null)
                                    .legalNoticeSentOn(null)
                                    .legalNoticeOutcome("")
                                    .cipReceivedOn(null)
                                    .cipSentOn(null)
                                    .cipOutcome("")
                                    .finNoticeReceivedOn(null)
                                    .finNoticeSentOn(null)
                                    .finOutcome("")
                                    .orgType(orgNomenclatures.get(faker.random().nextInt(0, orgNomenclatures.size()-1)))
                                    .salesAmount(Float.valueOf(faker.number().randomDigit()))
                                    .comment(faker.ancient().titan()).build()
                    );
                }
        );

        countDownLatch.countDown();

    }

}
