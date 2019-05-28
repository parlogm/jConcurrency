package ro.utm.jc;

import com.github.javafaker.Faker;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.model.entities.FidelityNomenclature;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ClientWorker implements Runnable {

    private List<FidelityNomenclature> fidelityNomenclatures;
    private CountDownLatch countDownLatch;
    private List<Client> clientList;

    public ClientWorker (List<FidelityNomenclature> fidelityNomenclatures,
            List<Client> clientList, CountDownLatch countDownLatch) {
        this.fidelityNomenclatures = fidelityNomenclatures;
        this.clientList = clientList;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run () {

        Faker faker = new Faker();

        IntStream.range(0, 10000).forEach(
                i -> {
                    clientList.add(
                            Client.builder()
                                    .isCorporate(faker.bool().bool())
                                    .name(faker.company().name())
                                    .createdAt(new Timestamp(new Date().getTime()))
                                    .updatedAt(new Timestamp(new Date().getTime()))
                                    .clientPriceGroup("stuff")
                                    .salesAgentId(faker.number().randomNumber())
                                    .fidelityNomenclature(fidelityNomenclatures.get(faker.random().nextInt(0, fidelityNomenclatures.size()-1)))
                                    .lastBillingDate(new Date())
                                    .billingRateIndex(Float.valueOf(faker.number().randomDigitNotZero()))
                                    .emailConfirmation(faker.bool().bool())
                                    .daysFromLastBill(0)
                                    .email(faker.internet().emailAddress())
                                    .assignedCenter("BUH")
                                    .country("Bucharest")
                                    .countryCode("B")
                                    .address(faker.address().fullAddress())
                                    .contactPhone(faker.phoneNumber().cellPhone())
                                    .contact(faker.name().fullName())
                                    .paymentMethod("Paypal")
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
                                    .orgType(faker.company().profession())
                                    .salesAmount(Float.valueOf(faker.number().randomDigit()))
                                    .comment(faker.ancient().titan()).build()
                    );
                }
        );
        countDownLatch.countDown();

    }

}
