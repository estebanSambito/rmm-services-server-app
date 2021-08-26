package com.esa.test.services.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.esa.test.services.server.security.JWTAuthorizationFilter;

@SpringBootApplication
//ESA TODO pasar a otro archivo de acuerdo a la documentacion ya que en las pruebas de controller se cae
//@EnableJpaAuditing
public class RmmServicesServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmmServicesServerAppApplication.class, args);
	}


	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
				.anyRequest().authenticated();
		}
	}
}
