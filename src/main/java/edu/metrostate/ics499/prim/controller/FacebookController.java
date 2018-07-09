package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The Facebook REST controller is used for registering Facebook account with PRIM.
 * Users do not directly interact with this controller. Instead, this controller is
 * used by PRIM.
 */
@RestController
@RequestMapping("/facebook")
public class FacebookController {

    @Autowired
    FacebookService facebookService;

    /**
     * Generates the Facebook authorization URL that the client is redirected to.
     *
     * @return the URL to redirect the client to.
     * @throws IOException
     */
    @GetMapping("/createFacebookAuthorizationUrl")
    public String createFacebookAuthorizationUrl() throws IOException {
        return facebookService.buildAuthorizationUrl();
    }

    /**
     * Registers Facebook with PRIM using the specified verification code.
     *
     * @param code the verification code received from Facebook after it redirects back to PRIM.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the social list page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/register")
    public void register(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        facebookService.registerFacebook(code);

        response.sendRedirect("/social/list");
    }

    /**
     * A convenience method that will get the Facebook authorization URL and redirect the client to it to start
     * the Facebook authorization process. Use this method if the client doesn't support JavaScript.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the Facebook Auth page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/registerByServer")
    public void registerByServer(HttpServletResponse response) throws IOException {
        response.sendRedirect(facebookService.buildAuthorizationUrl());
    }

    /**
     * Returns a list of all Facebook Post objects for all Facebook accounts registered with PRIM.
     *
     * @return a list of all Facebook Post objects for all Facebook accounts registered with PRIM.
     */
    @GetMapping("/feed")
    public List<Post> feed() {
        return facebookService.getAllPostTypeItems();
    }

}
