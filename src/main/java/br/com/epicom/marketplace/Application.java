package br.com.epicom.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe que inicia a aplicação
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan
public class Application {

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
	}
}

