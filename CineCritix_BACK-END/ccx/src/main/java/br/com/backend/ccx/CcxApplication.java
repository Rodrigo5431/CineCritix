package br.com.backend.ccx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.backend.ccx")
public class CcxApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcxApplication.class, args);
	}

}
