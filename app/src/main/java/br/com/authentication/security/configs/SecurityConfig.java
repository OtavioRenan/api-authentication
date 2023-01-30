package br.com.authentication.security.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.authentication.security.auth.UserDetailsServiceImp;
import br.com.authentication.security.auth.filters.AuthenticationFilter;
import br.com.authentication.security.auth.filters.ValidationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${contrate.security.token.expiration}")
    private int expiration;
	
    @Value("${contrate.security.token.key}")
	private String password;
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImp();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService service) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        
        return new ProviderManager(auth);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/login")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilter(new AuthenticationFilter(authenticationManager(userDetailsService()), expiration, password))
            .addFilter(new ValidationFilter(authenticationManager(userDetailsService()), password))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(HttpMethod.POST, "/api/user");
    }
}
