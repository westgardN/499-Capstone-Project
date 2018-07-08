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

import java.util.List;

@RestController
@RequestMapping("/facebook")
public class FacebookController {

    @Autowired
    FacebookService facebookService;

    @GetMapping("/createFacebookAuthorizationUrl")
    public String createFacebookAuthorizationUrl() {
        return facebookService.buildAuthorizationUrl();
    }

    @GetMapping("/register")
    public void register(@RequestParam("code") String code) {
        facebookService.registerFacebook(code);
    }

    @GetMapping("/feed")
    public List<Post> feed() {
        return facebookService.getAllPostTypeItems();
    }

//    private Facebook facebook;
//    private ConnectionRepository connectionRepository;
//
//    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
//        this.facebook = facebook;
//        this.connectionRepository = connectionRepository;
//    }
//
//    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
//    public String helloFacebook(Model model) {
//        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//            return "redirect:/connect/facebook";
//        }
//
//        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
//        PagedList<Post> feed = facebook.feedOperations().getFeed();
//        model.addAttribute("feed", feed);
//        return "facebook";
//    }
//
//    @Bean
//    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
//    public Facebook facebook() {
//        return connectionRepository.getPrimaryConnection(Facebook.class).getApi();
//    }
//
}
