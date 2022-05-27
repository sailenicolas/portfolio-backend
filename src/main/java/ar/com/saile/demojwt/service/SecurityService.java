package ar.com.saile.demojwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class SecurityService {

    static String APP_SECRET;

    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${app.secret}")
    public void setDirectory(String value) {

        APP_SECRET = value;
    }

    public static Algorithm getAlgorithm() {

        return Algorithm.HMAC512(APP_SECRET.getBytes());
    }

    public static String getToken(String token) {

        return token.replace(TOKEN_PREFIX, "");
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(String[] roles) {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    public static String createToken(User user, String issuer) {

        Date expiresAt = new Date(System.currentTimeMillis() + 6 * 60 * 60 * 1000);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());

    }

    public static String createRefreshToken(User user, String issuer) {

        Date expires = new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expires)
                .withIssuer(issuer)
                .sign(getAlgorithm());

    }
}
