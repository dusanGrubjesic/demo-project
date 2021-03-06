package com.toptal.demo.demoproject.configuration;

import com.toptal.demo.demoproject.configuration.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;

/**
 * @author dusan.grubjesic
 */
@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//				.authorizeRequests()
//				.antMatchers("/authentication/**").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable()
				.authorizeRequests().anyRequest().authenticated().and()
				.oauth2Login().authorizedClientRepository(new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService));
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
