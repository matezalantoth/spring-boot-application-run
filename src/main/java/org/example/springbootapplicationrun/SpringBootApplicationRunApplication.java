package org.example.springbootapplicationrun;

import org.example.springbootapplicationrun.components.clients.CarServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootApplicationRunApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootApplicationRunApplication.class, args);

	}

}
