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
import ro.utm.jc.model.entities.*;
import ro.utm.jc.service.*;

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
	private FidelityNomService fidelityNomService;
	@Autowired
	private CountryNomService countryNomService;
	@Autowired
	private PriceNomService priceNomService;
	@Autowired
	private CenterNomService centerNomService;
	@Autowired
	private PaymentNomService paymentNomService;
	@Autowired
	private OrgNomService orgNomService;

	public static void main(String[] args) throws Exception {

		new SpringApplication(JCApplication.class).run(args);

	}

	@Bean
	public CommandLineRunner generateRandomClients() {
		return args -> {

			Faker faker = new Faker();

			// generate fidelity groups
			if (fidelityNomService.findAll().isEmpty()) {
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

			if (countryNomService.findAll().isEmpty()) {
				countryNomService.saveAll(
						Arrays.asList(
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build(),
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build(),
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build(),
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build(),
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build(),
								CountryNomenclature.builder().country(faker.address().country()).code(faker.address().countryCode()).build()
						)
				);
			}

			if (priceNomService.findAll().isEmpty()) {
				priceNomService.saveAll(
						Arrays.asList(
								PriceNomenclature.builder().groupName("EndUser").build(),
								PriceNomenclature.builder().groupName("Reseller").build(),
								PriceNomenclature.builder().groupName("Distrib").build()
						)
				);
			}

			if (centerNomService.findAll().isEmpty()) {
				centerNomService.saveAll(
						Arrays.asList(
								CenterNomenclature.builder().name(faker.address().country()).build(),
								CenterNomenclature.builder().name(faker.address().country()).build(),
								CenterNomenclature.builder().name(faker.address().country()).build(),
								CenterNomenclature.builder().name(faker.address().country()).build(),
								CenterNomenclature.builder().name(faker.address().country()).build(),
								CenterNomenclature.builder().name(faker.address().country()).build()
						)
				);
			}

			if (paymentNomService.findAll().isEmpty()) {
				paymentNomService.saveAll(
						Arrays.asList(
								PaymentNomenclature.builder().methodName("OP").build(),
								PaymentNomenclature.builder().methodName("Cash ( at center )").build(),
								PaymentNomenclature.builder().methodName("BO").build(),
								PaymentNomenclature.builder().methodName("Repayment").build()
						)
				);
			}

			if (orgNomService.findAll().isEmpty()) {
				orgNomService.saveAll(
						Arrays.asList(
								OrgNomenclature.builder().type("Agency").build(),
								OrgNomenclature.builder().type("Embassy").build(),
								OrgNomenclature.builder().type("Individual").build(),
								OrgNomenclature.builder().type("State legal person").build(),
								OrgNomenclature.builder().type("Private legal person").build()
						)
				);
			}

		};

	}

}
