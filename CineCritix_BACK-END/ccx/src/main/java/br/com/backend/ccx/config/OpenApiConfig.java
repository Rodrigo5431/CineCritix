package br.com.backend.ccx.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${dominio.openapi.dev-url}")
	private String devUrl;

	@Value("${dominio.openapi.prod-url}")
	private String proUrl;

	@Bean
	@PreAuthorize("permitAll()")
	OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Url do servidor de desenvolvimento");

		Server prodServer = new Server();
		prodServer.setUrl(proUrl);
		prodServer.setDescription("Url do servidor de produção");

		Contact contact = new Contact();
		contact.setEmail("contato@burguer-master.com.br");
		contact.setName("Burguer-Master");
		contact.setUrl("https://www.burguer-master.com.br");

		License apacheLicense = new License().name("Apache License").url("https://www.apache.org/license/LICENSE-2.0");

		Info info = new Info().title("API para site de Criticas").version("1.0").contact(contact)
				.description("API de Burguer Master").termsOfService("https://www.meudominio.com.br/termos")
				.license(apacheLicense);

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}

}