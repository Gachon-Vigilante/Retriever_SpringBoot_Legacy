package com.team7.retriever.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team7.retriever.auth.jwt.filter.JwtAuthenticationFilter;
import com.team7.retriever.auth.security.CustomAccessDeniedHandler;
import com.team7.retriever.auth.security.CustomJwtAuthenticationEntryPoint;
import com.team7.retriever.entity.enums.Role;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomJwtAuthenticationEntryPoint customJwtAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	public String[] getAuthWhitelist() {
		return new String[] {
			"/error",
			"/auth/login",
			"/auth/reissue",
			"/swagger-ui/**",
			"/v3/api-docs/**",
		};
	}

	private static final String[] AUTH_ADMIN_ONLY = {
		"/auth/signup",
		"/user"
	};

	private static final String[] AUTH_ROOT_ONLY = {
		"/user/grant-role"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exception ->
				exception.authenticationEntryPoint(customJwtAuthenticationEntryPoint)
					.accessDeniedHandler(customAccessDeniedHandler));

		http.authorizeHttpRequests(auth ->
				auth.requestMatchers(getAuthWhitelist()).permitAll()
					.requestMatchers(AUTH_ADMIN_ONLY).hasAnyAuthority(Role.ADMIN.getRoleName(), Role.ROOT.getRoleName())
					.requestMatchers(AUTH_ROOT_ONLY).hasAuthority(Role.ROOT.getRoleName())
					.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
