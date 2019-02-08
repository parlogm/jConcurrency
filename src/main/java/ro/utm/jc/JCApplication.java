package ro.utm.jc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.*;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={ "ro.utm.jc.repo"})
@EntityScan(basePackages ={ "ro.utm.jc.model"})
@EnableTransactionManagement
public class JCApplication {

	public static void main(String[] args) throws Exception {

		new SpringApplication(JCApplication.class).run(args);

	}

}