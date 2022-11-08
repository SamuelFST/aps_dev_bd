package com.apsdevbd.apsdevbd.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String SECRET = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvXxWwYyZz1234567890";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String HEADER_STRING = "Authorization";
	private static final String SIGN_UP_URL = "/users/register";
	
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(
    		HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {
    	try {
    		String header = req.getHeader(HEADER_STRING);
    		String path = req.getRequestURI();
    		
    		if("/users/register".equals(path)) {
    			chain.doFilter(req, res);
    			return;
    		}
    		
    		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
    			res.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
    			chain.doFilter(req, res);
    			return;
    		}
    		
    		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    		
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		chain.doFilter(req, res);    		
    	} catch (IOException | ServletException ex) {
    		
    	}
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }
}