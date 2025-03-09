package io.github.frankleyrocha.springboot_pg_example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.frankleyrocha.springboot_pg_example.dto.PersonDTO;
import io.github.frankleyrocha.springboot_pg_example.service.PersonService;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringbootPgExampleApplication implements CommandLineRunner{

	private final PersonService personService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPgExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String domainName = "google.com";

		try {

			personService.create(new PersonDTO(
				null,
				"Bobby Fischer",
				"fischer@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

		try {

			personService.create(new PersonDTO(
			null,
			"Albert Einstein",
			"einstein@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

		try {

			personService.create(new PersonDTO(
			null,
			"Santos Dumont",
			"dumont@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

		try {

			personService.create(new PersonDTO(
			null,
			"Nikola Tesla",
			"tesla@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

		try {

			personService.create(new PersonDTO(
			null,
			"Benjamin Franklin",
			"franklin@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

		try {

			personService.create(new PersonDTO(
			null,
			"Alexander Graham Bell",
			"bell@%s".formatted(domainName)));

		}catch(Exception e){
			e.printStackTrace();
		};

	}

}
