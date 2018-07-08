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

@RestController
@RequestMapping("/facebook")
public class FacebookController {

    @Autowired
    FacebookService facebookService;

    @GetMapping("/createFacebookAuthorizationUrl")
    public String createFacebookAuthorizationUrl() throws IOException {
        return facebookService.buildAuthorizationUrl();
    }

    @GetMapping("/register")
    public void register(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        facebookService.registerFacebook(code);

        response.sendRedirect("/facebook/feed");
    }

    @GetMapping("/registerByServer")
    public void registerByServer(HttpServletResponse response) throws IOException {
        if (!facebookService.isRegistered()) {
        }

        response.sendRedirect(facebookService.buildAuthorizationUrl());
    }

    @GetMapping("/feed")
    public List<Post> feed() {
        return facebookService.getAllPostTypeItems();
    }

}
