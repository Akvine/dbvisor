package ru.akvine.dbvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DbvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbvisorApplication.class, args);
	}

}
