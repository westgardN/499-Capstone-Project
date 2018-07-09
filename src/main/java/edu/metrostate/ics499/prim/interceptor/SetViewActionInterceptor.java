package edu.metrostate.ics499.prim.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SetViewActionInterceptor class is called by Spring whenever a controller action is performed.
 * This class simply adds a variable named viewName to every view that is served from our application.
 */
public class SetViewActionInterceptor extends HandlerInterceptorAdapter
{
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception
    {
        if (modelAndView != null) {
            modelAndView.addObject("viewName", modelAndView.getViewName());
        }
    }
}
