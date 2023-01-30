package br.com.authentication.security.auth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ValidationFilter extends BasicAuthenticationFilter {

    private static final String ATRIBUTE = "Authorization";

    private static final String PREFIX = "Bearer ";

    private String password;

    public ValidationFilter(AuthenticationManager authenticationManager, String password) {
        super(authenticationManager);
        this.password = password;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
            String atributo = request.getHeader(ATRIBUTE);

            if(atributo == null) {
                chain.doFilter(request, response);
                return;
            }

            if(!atributo.startsWith(PREFIX)) {
                chain.doFilter(request, response);
                return;
            }

            String token = atributo.replace(PREFIX, "");

            UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String usuario = JWT.require(Algorithm.HMAC512(password))
            .build()
            .verify(token)
            .getSubject();

        if(Objects.isNull(usuario)) { return null; }

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
    }
}
