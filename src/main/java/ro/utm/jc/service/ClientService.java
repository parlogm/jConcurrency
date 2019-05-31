package ro.utm.jc.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.model.entities.CountryNomenclature;
import ro.utm.jc.model.entities.FidelityNomenclature;
import ro.utm.jc.repo.ClientRepo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public List<Client> findAll(Pageable p) {
        return findAll(p);
    }

    public Page<Client> findAll(Client clientVar, Pageable p) {
        return clientRepo.findAll(Example.of(clientVar), p);
    }

    public Boolean existsByName(String name) {
        return clientRepo.existsByName(name);
    }

    public Boolean existsById(Long id) {
        return clientRepo.existsById(id);
    }

    public void save(Client client) {
        clientRepo.save(client);
    }

    public void saveAll(List<Client> clients) {
        clientRepo.saveAll(clients);
    }

    public void deleteById(Long id) {
        clientRepo.deleteById(id);
    }

    public Client buildClient(List<FidelityNomenclature> fidelityNomenclatures, List<CountryNomenclature> countryNomenclatures, Faker faker) {
        return Client.builder()
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
                .countryNomenclature(countryNomenclatures.get(faker.random().nextInt(0, countryNomenclatures.size()-1)))
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
                .comment(faker.ancient().titan()).build();
    }

}
