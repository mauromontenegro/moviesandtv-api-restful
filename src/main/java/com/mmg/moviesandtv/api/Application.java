package com.mmg.moviesandtv.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * REST API that provides information about Movies, TV Shows and Actors/Actresses.
 * @Author Mauro Montenegro Guzmán (https://mauromontenegro.github.io/)
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(7000);
		factory.setReadTimeout(7000);
		return new RestTemplate(factory);
	}
}
