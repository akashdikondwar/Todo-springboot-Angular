package com.akash.rest.webservices.restful_web_services.jwtAuthService;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@CrossOrigin(origins = "http://localhost:4200/")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfig {
	
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
//        
//        // h2-console is a servlet 
//        // https://github.com/spring-projects/spring-security/issues/12310
//        return httpSecurity
//                .authorizeHttpRequests(auth -> auth
//                    .requestMatchers("/authenticate").permitAll()
//                    .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console is a servlet and NOT recommended for a production
//                    .requestMatchers(HttpMethod.OPTIONS,"/**")
//                    .permitAll()
//                    .anyRequest()
//                    .authenticated())
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.
//                    sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(
//                        (oauth2) -> oauth2.jwt(Customizer.withDefaults()))
//                .httpBasic(
//                        Customizer.withDefaults())
//                .headers(headers -> headers.frameOptions(frameOptionsConfig-> frameOptionsConfig.disable()))
//                .build();
//    }

		@Bean 
		SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests(
							auth -> 	
								auth
								.requestMatchers("/authenticate").permitAll()								
								.requestMatchers(HttpMethod.POST,"/signup").permitAll()
								.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
								.anyRequest().authenticated());
			
			http.sessionManagement(
							session -> 
								session.sessionCreationPolicy(
										SessionCreationPolicy.STATELESS)
							);
			
			//http.formLogin(); //commenting to avoid form login page
//			http.httpBasic();
			
			http.httpBasic(
		            Customizer.withDefaults());
			
			http.csrf(csrf -> csrf.disable());
			
			http.csrf(AbstractHttpConfigurer::disable);

			
		//	http.headers(headersConfigurer -> headersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));
			
			http.headers().frameOptions().sameOrigin();
			
			http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

			//the above server decodes the jwt token. and it uses decoder bean which we defined here.
			return http.build();
		}

//    @Bean
//    public AuthenticationManager authenticationManager(
//            UserDetailsService userDetailsService) {
//        var authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        return new ProviderManager(authenticationProvider);
//    }
    
    @Bean
    public DataSource datasource() {
    	return new EmbeddedDatabaseBuilder()
    			.setType(EmbeddedDatabaseType.H2)
    			.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService(DataSource datasource) {
        UserDetails user1 = User.withUsername("Akash")
                                .password("Akash@123")
                                .passwordEncoder(str -> passwordEncoder().encode(str))
                                .authorities("read")
                                .roles("USER")
                                .build();
        
        UserDetails user2 = User.withUsername("Sushil")
                .password("Sushi@123")
                .passwordEncoder(str -> passwordEncoder().encode(str))
                .authorities("read")
                .roles("USER")
                .build();
        
        var jdbcUserDetailsManager = new JdbcUserDetailsManager(datasource);
        jdbcUserDetailsManager.createUser(user1);
        jdbcUserDetailsManager.createUser(user2);
        return jdbcUserDetailsManager;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        JWKSet jwkSet = new JWKSet(rsaKey());
        return (((jwkSelector, securityContext) 
                        -> jwkSelector.select(jwkSet)));
    }
    
  
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey().toRSAPublicKey())
                .build();
    }
    
    @Bean
    public KeyPair keyPair() {
    	try {
    		var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    		keyPairGenerator.initialize(2048);
    		return keyPairGenerator.generateKeyPair();
    	} catch (Exception e) {
    		throw new IllegalStateException(
    				"Unable to generate an RSA Key Pair", e);
    	}
    }
    
    
    @Bean
    public RSAKey rsaKey() {
        
        KeyPair keyPair = keyPair();
        
        return new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    
}

