package br.com.authentication.security.auth.filters;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.authentication.security.auth.LoginDTO;
import br.com.authentication.security.auth.UserDetailsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
	
	private int expiration;
	
	private String password;

    public AuthenticationFilter(AuthenticationManager authenticationManager, int expiration, String password) {
        this.authenticationManager = authenticationManager;
        this.expiration = expiration;
        this.password = password;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginDTO user;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getLogin(), user.getPassword(), new ArrayList<>()));
        } catch (IOException | AuthenticationException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("Erro na autenticação.", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
            UserDetailsData user = (UserDetailsData) authResult.getPrincipal();

            String result = response(user);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(result);
            response.getWriter().flush();
    }

    private String response(UserDetailsData u) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();

		map.put("expiration", new Timestamp(System.currentTimeMillis() + expiration).toString());
		map.put("token", JWT.create().withSubject(u.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
            .sign(Algorithm.HMAC512(password)));

		return new ObjectMapper().writeValueAsString(map);
    }
}
