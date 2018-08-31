package edu.metrostate.ics499.prim.configuration;

import edu.metrostate.ics499.prim.component.PrimAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

        PrimAuthenticationFailureHandler primAuthenticationFailureHandler = (PrimAuthenticationFailureHandler) authenticationFailureHandler;

        primAuthenticationFailureHandler.setDefaultFailureUrl("/login");
        //primAuthenticationFailureHandler.setUseForward(true);

        http.authorizeRequests()
                .antMatchers("/", "/home", "/login", "/logout")
                .permitAll()
                .antMatchers("/user/profile", "/user/changePassword", "/interaction/**", "/report/**", "/interaction/ignore/*", "/social/refresh", "/social/list")
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .antMatchers("/user/list", "/user/new/**", "/user/delete/*", "/social/register", "/social/delete/**", "/facebook/*", "/twitter/*", "/linkedin/*")
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
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
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

    /**
     * Override this method to expose the {@link AuthenticationManager} from
     * {@link #configure(AuthenticationManagerBuilder)} to be exposed as a Bean. For
     * example:
     *
     * <pre>
     * &#064;Bean(name name="myAuthenticationManager")
     * &#064;Override
     * public AuthenticationManager authenticationManagerBean() throws Exception {
     *     return super.authenticationManagerBean();
     * }
     * </pre>
     *
     * @return the {@link AuthenticationManager}
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}