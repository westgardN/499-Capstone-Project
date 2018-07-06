package edu.metrostate.ics499.prim.configuration;

import edu.metrostate.ics499.prim.converter.RoleIdToRoleTypeConverter;
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
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values[Roles] to UserProfiles in newUser.jsp
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleIdToRoleTypeConverter);
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

    /**
     * Required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in
     * @PathVairables argument. It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present
     * in Spring 4.1.7. This is a workaround for this issue.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    /**
     * Adds associations between views and controller endpoints
     * @param registry the controller registry we will be adding to.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/greetingweb").setViewName("greeting");
//        registry.addViewController("/greetingwebgroovy").setViewName("greetinggroovy");
        registry.addViewController("/hello").setViewName("hello");
//        registry.addViewController("/login").setViewName("login");
    }

}