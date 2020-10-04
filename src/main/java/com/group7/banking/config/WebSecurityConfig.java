package com.group7.banking.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.group7.banking.service.BankingUserDetailsService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BankingUserDetailsService bankingUserDetailsService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests() .antMatchers("/", "/login", "/sign-up").permitAll()
		 * .and() .formLogin().loginPage("/login");
		 */
		http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        
		/*
		 * http.cors().and().csrf().disable().authorizeRequests()
		 * .antMatchers("/**").permitAll();
		 */
		
		/*
		 * http.cors().and().csrf().disable() .authorizeRequests()
		 * .antMatchers("/sign-up/**", "/login/**") .permitAll() .anyRequest()
		 * .authenticated() .and() .formLogin() .permitAll();
		 */
		
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/sign-up/**", "/login/**").permitAll()
        .and()
        .formLogin()
        .loginProcessingUrl("/perform_login")
        .and()
        .logout()
        .logoutUrl("/perform_logout")
        .deleteCookies("JSESSIONID");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(bankingUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}
}