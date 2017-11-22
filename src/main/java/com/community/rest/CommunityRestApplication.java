package com.community.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CommunityRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityRestApplication.class, args);
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Bean
		InMemoryUserDetailsManager userDetailsManager() {
			User.UserBuilder commonUser = User.withUsername("commonUser");
			User.UserBuilder havi = User.withUsername("havi");

			List<UserDetails> userDetailsList = new ArrayList<>();
			userDetailsList.add(commonUser.password("common").roles("USER").build());
			userDetailsList.add(havi.password("test").roles("USER", "ADMIN").build());

			return new InMemoryUserDetailsManager(userDetailsList);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic()
					.and().authorizeRequests()
					.antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
					.and().csrf().disable();
		}
	}
}
