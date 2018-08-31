package edu.metrostate.ics499.prim.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This empty class definition is required in order to register the DelegatingFilterProxy to
 * user the springSecurityFilterChain. This empty class also registers spring's
 * ContextLoaderListener that starts up and shuts down the web application context.
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}