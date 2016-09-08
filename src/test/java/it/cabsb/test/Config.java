package it.cabsb.test;

import it.cabsb.persistence.service.IUserService;
import it.cabsb.persistence.service.impl.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"it.cabsb.persistence"})
public class Config {

	@Bean
	public IUserService getUserService() {
		return new UserService();
	}
}
