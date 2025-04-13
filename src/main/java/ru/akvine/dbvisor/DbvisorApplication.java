package ru.akvine.dbvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DbvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbvisorApplication.class, args);
	}

}
