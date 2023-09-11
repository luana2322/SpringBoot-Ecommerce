package com.Admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages={"com.Admin","com.Core"})
@EnableJpaRepositories
@EntityScan(value="com.Core.model")
public class AdminApplication{
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
}
