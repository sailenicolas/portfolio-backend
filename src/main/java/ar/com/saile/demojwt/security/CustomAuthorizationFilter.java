package ar.com.saile.demojwt.security;

import ar.com.saile.demojwt.domain.AppUser;
import ar.com.saile.demojwt.service.SecurityService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ar.com.saile.demojwt.service.SecurityService.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {

        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JWTVerificationException, ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(403);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("errorMessage", e.getMessage());
            tokens.put("errorCode", String.valueOf(FORBIDDEN.value()));
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }
    }


    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws JWTVerificationException {

        String token = request.getHeader(AUTHORIZATION);
        if (token != null) {
            Algorithm algorithm = SecurityService.getAlgorithm();
            token = SecurityService.getToken(token);
            DecodedJWT user = JWT.require(
                            algorithm
                    )
                    .build()
                    .verify(token);

            String[] roles = user.getClaim("roles").asArray(String.class);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, SecurityService.getAuthorities(roles));
            usernamePasswordAuthenticationToken
                    .setDetails(new AppUser(user.getSubject()));
            return usernamePasswordAuthenticationToken;
        }
        return null;
    }
}
