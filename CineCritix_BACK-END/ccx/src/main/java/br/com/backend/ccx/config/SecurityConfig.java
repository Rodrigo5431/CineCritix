package br.com.backend.ccx.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.backend.ccx.security.JwtAuthenticationFilter;
import br.com.backend.ccx.security.JwtAuthorizationFilter;
import br.com.backend.ccx.security.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtUtil jwt;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors((cors) -> cors.configurationSource(configurationSource()))
				.authorizeHttpRequests(requests -> requests.requestMatchers("/public/**").permitAll()

						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/login").permitAll()
						.anyRequest()
						.permitAll())
						//.authenticated())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.headers((headers) -> headers.disable());

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwt);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");
		http.addFilter(jwtAuthenticationFilter);
		
		http.addFilter(new JwtAuthorizationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwt,
				userDetailsService));
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "projectcinecritixback-end-production.up.railway.app", "https://cinecritix.netlify.app"));

		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());

		return source;
	}
	
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
