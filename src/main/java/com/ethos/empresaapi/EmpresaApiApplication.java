package com.ethos.empresaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EmpresaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpresaApiApplication.class, args);
        System.out.println("Teste watchtower test ec2");
    }

}
