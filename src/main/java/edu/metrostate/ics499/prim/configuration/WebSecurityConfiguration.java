package edu.metrostate.ics499.prim.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * The WebSecurityConfiguration class extends the Spring WebSecurityConfigurerAdapter class.
 * The purpose of this class is to define how Spring Security is configured for the application in lieu of using XML.
 * This class defines the security on the endpoints of the application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("primUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    PersistentTokenRepository tokenRepository;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home", "/login", "/logout")
                .permitAll()
                .antMatchers("/user/profile", "/user/changePassword", "/interaction/*", "/report/*")
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .antMatchers("/user/list", "/user/new/**", "/user/delete/*", "/social/*", "/facebook/*", "/twitter/*", "/linkedin/*")
                .access("hasRole('ADMIN')")
                .antMatchers("/user/edit/*")
                .access("hasRole('ADMIN') or hasRole('DBA')")
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login")
                .failureHandler(authenticationFailureHandler)
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("ssoId")
                .passwordParameter("password")
                .and()
                .rememberMe().
                rememberMeParameter("remember-me")
                .tokenRepository(tokenRepository)
                .tokenValiditySeconds(86400)
                .and()
                .csrf()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
        return new PersistentTokenBasedRememberMeServices("remember-me", userDetailsService, tokenRepository);
    }

    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

}