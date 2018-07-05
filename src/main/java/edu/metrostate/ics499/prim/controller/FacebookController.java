package edu.metrostate.ics499.prim.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
public class FacebookController {
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
