package co.com.bancolombia.mscurrencytest.infrastructure.config.security;

import co.com.bancolombia.mscurrencytest.infrastructure.config.security.filter.JwtFilter;
import co.com.bancolombia.mscurrencytest.infrastructure.service.IUserGeneralService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static co.com.bancolombia.mscurrencytest.infrastructure.config.security.SecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
public class WebSecurityCustomConfiguration extends WebSecurityConfigurerAdapter {

    private final IUserGeneralService iUserGeneralService;
    private final PasswordEncoder passwordEncoder;


    public WebSecurityCustomConfiguration(IUserGeneralService iUserGeneralService, PasswordEncoder passwordEncoder) {
        this.iUserGeneralService = iUserGeneralService;
        this.passwordEncoder = passwordEncoder;
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        //sign Up access enabled to public
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, SIGN_UP_URL)
                .permitAll()
                .mvcMatchers(HttpMethod.POST, "/user/login")
                .permitAll()
                //swagger and open api
                .mvcMatchers("/v3/api-docs/**")
                .permitAll()
                .mvcMatchers("/swagger-ui/**")
                .permitAll()
                .mvcMatchers("/swagger-ui.html")
                .permitAll()
                //any other request must be authenticated
                .anyRequest()
                .authenticated();

        http.addFilterBefore(new JwtFilter(iUserGeneralService), UsernamePasswordAuthenticationFilter.class);
        //http.addFilter(new JwtAuthenticationFilter(authenticationManager()));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iUserGeneralService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/h2-console/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


}
