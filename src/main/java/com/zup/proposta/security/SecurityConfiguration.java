package com.zup.proposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests(authorizeRequests -> authorizeRequests
                .mvcMatchers("**/actuator/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/cartoes/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/cartoes/**").hasAuthority("SCOPE_proposta")
                .mvcMatchers(HttpMethod.POST, "/biometrias/**").hasAuthority("SCOPE_proposta")
                .mvcMatchers(HttpMethod.POST, "/propostas/**").hasAuthority("SCOPE_proposta")
                .mvcMatchers(HttpMethod.GET, "/propostas/**").hasAuthority("SCOPE_proposta")
                .mvcMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
        )
		.cors().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
	}

}
