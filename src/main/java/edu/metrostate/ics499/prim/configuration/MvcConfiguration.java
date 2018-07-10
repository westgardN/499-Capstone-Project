package edu.metrostate.ics499.prim.configuration;

import edu.metrostate.ics499.prim.converter.RoleIdToRoleTypeConverter;
import edu.metrostate.ics499.prim.interceptor.SetViewActionInterceptor;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * The WebMvcConfiguration class implements the Spring WebMvcConfigurer. It is used in lieu of
 * configuring the application via an XML file. Its primary purpose is to define a view resolver for JSPs,
 * setup location for serviing static resources like JavaScript and CSS files, converters, and a message
 * source for internationalization of messages.
 */
@Configuration
@ComponentScan(basePackages = "edu.metrostate.ics499.prim")
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    RoleIdToRoleTypeConverter roleIdToRoleTypeConverter;

    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values[Roles] to Roles in newUser.html
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleIdToRoleTypeConverter);
    }

    /**
     * Adds associations between views and controller endpoints for the home page.
     * @param registry the controller registry we will be adding to.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
    }

    /**
     * Required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in
     * @PathVairables argument. It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present
     * in Spring 4.1.7. This is a workaround for this issue.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(sec); // Enable use of "sec"
        templateEngine.addDialect(layoutDialect());
        return templateEngine;
    }

    /**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Override
    public void addInterceptors(InterceptorRegistry reg)
    {
        reg.addInterceptor(new SetViewActionInterceptor());
    }
}