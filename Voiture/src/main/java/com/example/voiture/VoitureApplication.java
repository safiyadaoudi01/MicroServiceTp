package com.example.voiture;

import com.example.voiture.Model.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.voiture.Service")
@ComponentScan(basePackages = "com.example.voiture")
public class VoitureApplication {

	@FeignClient(name="SERVICE-CLIENT")
	@Component
	public interface ClientService{
		@GetMapping(path="/clients/{id}")
		public Client clientById(@PathVariable Long id);
	}

	public static void main(String[] args) {
		SpringApplication.run(VoitureApplication.class, args);
	}



}
