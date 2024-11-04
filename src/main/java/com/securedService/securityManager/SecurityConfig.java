package com.securedService.securityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	protected SecurityFilterChain configureaut(HttpSecurity https) throws Exception {

		return https
				.csrf(customazer-> customazer.disable())  // This will disable the csrf. Need to check if we write only https.build() then csrf will work or not.
				.authorizeHttpRequests(req -> req.anyRequest().authenticated()) // This will authenticate and authorise each and every request.
				.formLogin(Customizer.withDefaults()) // This will give login form in browser and postman also. For working in postman we need to call method .httpBasic() in below
				.httpBasic(Customizer.withDefaults()) // This is for working in postman 
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // This will make the session Stateless (definition given below)
				.build(); // It will return a combined constructor for above filds

	}

}
////////////////////////  Tutorial /////////////////////////////////////

//1. Stateful vs Stateless session:-
//   Stateful session is the session where the application remembers the sessionId throughout the time until logout or clear cache.
//   But stateless session is the session where App will generate new sessionId for each and every request. Thats why for each localhost:8080 we are getting same login page cause in every hit of the url its generating new sessionId.
//   But for postman it will work.
