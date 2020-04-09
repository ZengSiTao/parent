package com.linktop.cloud.entranceguardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients(basePackages = "com.linktop.cloud.entranceguardclient")
public class EntranceGuardAdminApplication {

	public static void main(String[] args) {

		SpringApplication.run(EntranceGuardAdminApplication.class, args);
	}

}
