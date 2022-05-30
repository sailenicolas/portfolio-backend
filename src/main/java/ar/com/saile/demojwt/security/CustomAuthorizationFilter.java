package ar.com.saile.demojwt.security;

import ar.com.saile.demojwt.service.SecurityService;
import ar.com.saile.demojwt.service.UserServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

import static ar.com.saile.demojwt.service.SecurityService.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserServiceImpl userDetailsService;

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager, UserServiceImpl userDetailsService) {

        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws JWTVerificationException, ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            throw new JWTVerificationException("NOT AUTH PERMITED");
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
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
            System.out.println("roles = " + user.getClaim("roles"));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, SecurityService.getAuthorities(roles));
            usernamePasswordAuthenticationToken
                    .setDetails(userDetailsService.findByUsername(user.getSubject()));
            return usernamePasswordAuthenticationToken;
        }
        return null;
    }
}
