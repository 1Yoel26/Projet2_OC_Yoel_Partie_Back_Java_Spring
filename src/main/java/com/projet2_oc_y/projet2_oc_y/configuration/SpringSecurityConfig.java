package com.projet2_oc_y.projet2_oc_y.configuration;

import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;

@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private UsersService userService;
	
	@Bean
	public SecurityFilterChain chaineDeFiltre(HttpSecurity http) throws Exception {
		
		// ajouter dedans le cross origin pour lier le front et le back.
		return http
				.cors(cors -> cors.configurationSource(requete -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(List.of("http://localhost:4200")); // Origine front Angular
	                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // type de Http autorisé depuis angular
	                config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // pour envoyer le token jwt dans le header
	                
	                return config;
				}))
				.csrf( csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth
						.requestMatchers("/api/auth/login").permitAll() 
						.requestMatchers("/api/auth/register").permitAll() 
						.requestMatchers("/Images/*").permitAll()
						.requestMatchers(
							    "/swagger-ui/**",
							    "/v3/api-docs/**",
							    "/v3/api-docs",
							    "/swagger-resources/**"
							).permitAll()
						.anyRequest().authenticated()
						)
				.oauth2ResourceServer((oauth2)-> oauth2.jwt(Customizer.withDefaults()))
				.build();
	}
	
	
	// c'est le mdp chiffré 
	private String cleJwt = "yJ7wXcT9F4oN0X6vKvG2s7R1Uz8p+qXyN0fJgMdL5A0=";

	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder encoder) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	        .userDetailsService(userService)
	        .passwordEncoder(encoder)
	        .and()
	        .build();
	}
	
	
	@Bean
	public JwtDecoder decodageJwt() {
		
		// transforme le mdp pour qu'il soit utilisable pour la crypto en java.
		SecretKeySpec cleSecrete = new SecretKeySpec(this.cleJwt.getBytes(), 0, this.cleJwt.getBytes().length,"RSA");
		
		// fonction appelé a chaque requete dans l'app pour verifier si le token est valide
		return NimbusJwtDecoder.withSecretKey(cleSecrete).macAlgorithm(MacAlgorithm.HS256).build();
	}
	
	
	@Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.cleJwt.getBytes()));
    }
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	} 
	
	

}
