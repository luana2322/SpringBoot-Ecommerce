package com.Customer.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomerConfiguration {

	
	@Bean
	public UserDetailsService userDetailsService() {
		
		return new CustomerServiceConfig();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain	(HttpSecurity http) throws Exception {
		  http.authorizeRequests()
	        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
	         .requestMatchers("/*").permitAll()
	         .requestMatchers("/customer/*").hasAnyAuthority("CUSTOMER")
	          .and()
	          .formLogin()
	          .usernameParameter("username")
              .passwordParameter("password")
	          .loginPage("/login")
	          .loginProcessingUrl("/do-login")
	          .defaultSuccessUrl("/products")
	          .failureUrl("/login?error")
	          .permitAll()
	        .and()
	          .logout()
	          .logoutSuccessUrl("/login?logout")
	          .invalidateHttpSession(true)
	          .deleteCookies("JSESSIONID")
	          .permitAll()
	        .and()
	          .csrf()
	          .disable();


        return http.build();
    }  
}
