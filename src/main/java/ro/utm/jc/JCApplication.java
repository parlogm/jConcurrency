package ro.utm.jc;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.*;
import ro.utm.jc.model.entities.Client;
import ro.utm.jc.service.ClientService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={ "ro.utm.jc.repo"})
@EntityScan(basePackages ={ "ro.utm.jc"})
@EnableTransactionManagement
public class JCApplication {

	@Autowired
	private ClientService clientService;

	public static void main(String[] args) throws Exception {

		new SpringApplication(JCApplication.class).run(args);

	}

	@Bean
	public CommandLineRunner generateRandomClients() {
		return args -> {
			List<Client> clientList = new ArrayList<>();
			Faker faker = new Faker();
			IntStream.range(0, 100).forEach(
					i -> {
						clientList.add(
								Client.builder()
										.isCorporate(faker.bool().bool())
										.name(faker.company().name())
										.createdAt(new Timestamp(new Date().getTime()))
										.updatedAt(new Timestamp(new Date().getTime()))
										.clientPriceGroup(faker.company().profession())
										.salesAgentId(faker.number().randomNumber())
										.fidelityCardId(faker.number().randomNumber())
										.fidelityGroup(faker.ancient().god())
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
										.finNoticeOutcome("")
										.orgType(faker.company().profession())
										.salesAmount(Float.valueOf(faker.number().randomDigit()))
										.comment(faker.ancient().titan()).build()
						);
					}
			);
			clientService.saveAll(clientList);
		};
	}

}