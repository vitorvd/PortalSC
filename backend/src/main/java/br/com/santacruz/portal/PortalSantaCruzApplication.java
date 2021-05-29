package br.com.santacruz.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
@EntityScan(basePackages = PortalSantaCruzApplication.PACOTE_MODELO)
@ComponentScan(basePackages = PortalSantaCruzApplication.BASE_COMPONENT)
@EnableJpaRepositories(PortalSantaCruzApplication.BASE_REPOSITORY)
public class PortalSantaCruzApplication extends ServletInitializer{

	public static final String BASE_COMPONENT = "br.com.santacruz.portal";

	public static final String BASE_REPOSITORY = BASE_COMPONENT + ".dao"; //Jpa Repository

	public static final String PACOTE_MODELO = BASE_COMPONENT + ".modelo";

	public static void main(String[] args) {
		SpringApplication.run(PortalSantaCruzApplication.class, args);
	}

}
