package com.securedService.securityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetails;

	// 1
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
	
	// 2
//	@Bean
//	public UserDetailsService userDetails() {
//		
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("Sumit")
//				.password("Sumit@123")
//				.roles("Admin")
//				.build();
//		
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("Saki")
//				.password("Saki@123")
//				.roles("Admin")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1,user2); // This will deactivate application.properties user details(i.e. username and password) and implement new user details which is mentioned here under user1 and user2.
//	}
	
	
	// 3.
	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); 
		provider.setUserDetailsService(userDetails);
		return provider;
	}
	

}
////////////////////////  Tutorial /////////////////////////////////////

//1. Stateful vs Stateless session:-
//   Stateful session is the session where the application remembers the sessionId throughout the time until logout or clear cache.
//   But stateless session is the session where App will generate new sessionId for each and every request. Thats why for each localhost:8080 we are getting same login page cause in every hit of the url its generating new sessionId.
//   But for postman it will work.

/////////////2. We should not use hardcoded user details in App 

/////////////3. So we need to get it from Database.
/////////////// When we submit login form, its un-authenticated object that goes to server. Then It goes to an 
             // Authentication provider after that it makes it an authenticated object. Here we make our own authentication provider
//    AuthenticationProvider is an interface thats why we need to find some class that implements that interface.
//    For Authentication from database we have a class named DaoAuthenticationProvider. So we return DaoAuthentication provider object.