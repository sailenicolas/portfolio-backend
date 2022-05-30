package ar.com.saile.demojwt.security;

import ar.com.saile.demojwt.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class CustomWebSecurity extends WebSecurityConfigurerAdapter {

    static final String API_V_1 = "/api/v1/";

    private final UserServiceImpl userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomExceptionHandler customExceptionHandler;

    public static String LOGIN_URL;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        LOGIN_URL = API_V_1 + "user/login";
        customAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        http.csrf().disable();
        http.addFilterBefore(customExceptionHandler, LogoutFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(LOGIN_URL).permitAll();
        http.addFilter(customAuthenticationFilter);
        http.addFilter(new CustomAuthorizationFilter(authenticationManager(), userDetailsService));
        http.authorizeRequests().antMatchers(HttpMethod.POST, API_V_1 + "user/**").hasAnyRole("ADMIN", "SUPER_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, API_V_1 + "role/**").hasAnyRole("ADMIN", "SUPER_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }
}
