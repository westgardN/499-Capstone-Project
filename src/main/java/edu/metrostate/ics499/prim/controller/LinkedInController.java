package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.service.LinkedInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;

import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The LinkedIn REST controller is used for registering LinkedIn account with PRIM.
 * Users do not directly interact with this controller. Instead, this controller is
 * used by PRIM.
 */
@RestController
@RequestMapping("/linkedin")
public class LinkedInController {

    @Autowired
    LinkedInService linkedInService;

    /**
     * Generates the LinkedIn authorization URL that the client is redirected to.
     *
     * @return the URL to redirect the client to.
     * @throws IOException
     */
    @GetMapping("/createLinkedInAuthorizationUrl")
    public String createLinkedInAuthorizationUrl() throws IOException {
        return linkedInService.buildAuthorizationUrl();
       
    }

    /**
     * Registers LinkedIn with PRIM using the specified verification code.
     *
     * @param code the verification code received from LinkedIn after it redirects back to PRIM.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the social list page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/register")
    public void register(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
    	linkedInService.registerLinkedIn(code);
    	

        response.sendRedirect("/social/list");
    }

    /**
     * A convenience method that will get the LinkedIn authorization URL and redirect the client to it to start
     * the LinkedIn authorization process. Use this method if the client doesn't support JavaScript.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the LinkedIn Auth page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/registerByServer")
    public void registerByServer(HttpServletResponse response) throws IOException {
        response.sendRedirect(linkedInService.buildAuthorizationUrl());
    }

    /**
     * Returns a list of all LinkedIn Post objects for all LinkedIn accounts registered with PRIM.
     *
     * @return a list of all LinkedIn Post objects for all LinkedIn accounts registered with PRIM.
     */
    @GetMapping("/feed")
    public List<Post> feed() {
        return linkedInService.getAllPostTypeItems();
   
    }

}
