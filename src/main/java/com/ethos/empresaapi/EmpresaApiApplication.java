package com.ethos.empresaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmpresaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresaApiApplication.class, args);
	}

}
