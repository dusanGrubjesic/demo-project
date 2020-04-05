package com.toptal.demo.demoproject.configuration.filters;

import com.toptal.demo.demoproject.services.UserDetailsServiceImpl;
import com.toptal.demo.demoproject.services.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dusan.grubjesic
 */
@Component
public class JwtRequestFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;


	public JwtRequestFilter() {
		super(new AntPathRequestMatcher("/api/**"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		final String authorizationHeader = request.getHeader("Authorization");
		String jwt = null;
		String userName = null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			userName = jwtUtil.extractUserName(jwt);
		}
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
		if (!jwtUtil.validate(jwt)) {
			throw new AccountExpiredException("Token expired");
		}
		if (userDetails == null) {
			throw new BadCredentialsException("Token invalid");
		}
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()
				, userDetails.getAuthorities());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(request.getRequestURI()));
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
}
