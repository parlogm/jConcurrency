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
import ro.utm.jc.model.entities.FidelityNomenclature;
import ro.utm.jc.service.ClientService;
import ro.utm.jc.service.FidelityNomService;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={ "ro.utm.jc.repo"})
@EntityScan(basePackages ={ "ro.utm.jc"})
@EnableTransactionManagement
public class JCApplication {

	@Autowired
	private ClientService clientService;
	@Autowired
	private FidelityNomService fidelityNomService;

	public static void main(String[] args) throws Exception {

		new SpringApplication(JCApplication.class).run(args);

	}

	@Bean
	public CommandLineRunner generateRandomClients() {
		return args -> {

			// generate fidelity groups
			if (fidelityNomService.findAll().size() == 0) {
				fidelityNomService.saveAll(
						Arrays.asList(FidelityNomenclature.builder().groupName("Rhodium").build(),
								FidelityNomenclature.builder().groupName("Platinum").build(),
								FidelityNomenclature.builder().groupName("Gold").build(),
								FidelityNomenclature.builder().groupName("Palladium").build(),
								FidelityNomenclature.builder().groupName("Iridium").build(),
								FidelityNomenclature.builder().groupName("Osmium").build(),
								FidelityNomenclature.builder().groupName("Rhenium").build(),
								FidelityNomenclature.builder().groupName("Ruthenium").build(),
								FidelityNomenclature.builder().groupName("Germanium").build(),
								FidelityNomenclature.builder().groupName("Beryllium").build(),
								FidelityNomenclature.builder().groupName("Silver").build()));
			}

			final List<FidelityNomenclature> fidelityNomenclatures = fidelityNomService.findAll();

			Instant start = Instant.now();

			/*List<Client> clientList = Collections.synchronizedList(new ArrayList<>());

			CountDownLatch countDownLatch = new CountDownLatch(10);
			List<Thread> clientWorkerThreads = Stream
					.generate(() -> new Thread(new ClientWorker(fidelityNomenclatures, clientList, countDownLatch)))
					.limit(10)
					.collect(toList());

			clientWorkerThreads.forEach(Thread::start);
			countDownLatch.await();
			System.out.println("Latch released");*/

			/*List<Client> clientList = new ArrayList<>();
			Faker faker = new Faker();
			IntStream.range(0, 100).forEach(
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
			);*/

			//clientService.saveAll(clientList);
			Instant finish = Instant.now();
			System.out.println("Time taken to finish generation of clients is : " + Duration.between(start, finish).toMillis() + " miliseconds");
		};

	}

}
