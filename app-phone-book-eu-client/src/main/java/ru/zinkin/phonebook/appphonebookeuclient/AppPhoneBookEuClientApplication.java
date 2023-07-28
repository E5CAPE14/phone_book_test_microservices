package ru.zinkin.phonebook.appphonebookeuclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppPhoneBookEuClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppPhoneBookEuClientApplication.class, args);
	}

}
