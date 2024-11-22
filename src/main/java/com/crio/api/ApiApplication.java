package com.crio.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		// Carregar o arquivo .env
		Dotenv dotenv = Dotenv.load();

		// Exemplo: puxar uma variável do .env
		String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
		System.out.println("Google Client ID: " + googleClientId);

		SpringApplication.run(ApiApplication.class, args);
	}

}
