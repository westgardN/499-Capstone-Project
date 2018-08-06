package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The Twitter REST controller is used for registering Twitter account with PRIM.
 * Users do not directly interact with this controller. Instead, this controller is
 * used by PRIM.
 */
@RestController
@RequestMapping("/twitter")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    /**
     * Generates the Twitter authorization URL that the client is redirected to.
     *
     * @return the URL to redirect the client to.
     * @throws IOException
     */
    @GetMapping("/createTwitterAuthorizationUrl")
    public String createTwitterAuthorizationUrl() throws IOException {
        return twitterService.buildAuthorizationUrl();
    }

    /**
     * Registers Twitter with PRIM using the specified verification code.
     *
     * @param code the verification code received from Twitter after it redirects back to PRIM.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the social list page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/register")
    public void register(@RequestParam("oauth_token") String oauthToken, @RequestParam("oauth_verifier") String oauthVerifier, HttpServletResponse response) throws IOException {
        twitterService.registerTwitter(oauthToken, oauthVerifier);

        response.sendRedirect("/social/list");
    }

    /**
     * A convenience method that will get the Twitter authorization URL and redirect the client to it to start
     * the Twitter authorization process. Use this method if the client doesn't support JavaScript.
     *
     * @param response the HttpServletResponse we will use to redirect the client to the Twitter Auth page.
     *
     * @throws IOException thrown by the HttpServletResponse if an error occurs.
     */
    @GetMapping("/registerByServer")
    public void registerByServer(HttpServletResponse response) throws IOException {
        response.sendRedirect(twitterService.buildAuthorizationUrl());
    }

    /**
     * Returns a list of all Tweet objects for all Twitter accounts registered with PRIM.
     *
     * @return a list of all Tweet objects for all Twitter accounts registered with PRIM.
     */
    @GetMapping("/feed")
    public List<Tweet> feed() {
        return twitterService.getAllTweetTypeItems();
    }

}
