package edu.metrostate.ics499.prim.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This class simply registers the DispatcherServlet via JavaConfig instead of an XML
 * configuration file.
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer  {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { MvcConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }
}
