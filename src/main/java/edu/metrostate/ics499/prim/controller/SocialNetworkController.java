package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.service.InteractionService;
import edu.metrostate.ics499.prim.service.SentimentService;
import edu.metrostate.ics499.prim.service.SocialNetworkRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The SocialNetworkController class handles all social networking requests
 * for PRIM
 */
@Controller
@RequestMapping("/social")
public class SocialNetworkController {

    private static final Logger logger = LoggerFactory.getLogger(SocialNetworkController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SocialNetworkRegistrationService socialNetworkRegistrationService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private SentimentService sentimentService;

    /**
     * This method will list all existing registered social accounts.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public ModelAndView listRegisteredAccounts(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<SocialNetworkRegistration> accounts = socialNetworkRegistrationService.findAll();
        ModelAndView mav = new ModelAndView("/social/socialList", "accounts", accounts);
        mav.getModelMap().addAttribute("deleteUrl", buildUrl(request, "social/delete/"));

        return mav;
    }

    @ResponseBody
    @RequestMapping(value =  { "/delete/{id}" }, method = RequestMethod.POST)
    public void deleteSocialAccount(@PathVariable("id") int id, HttpServletRequest request) {
        socialNetworkRegistrationService.deleteById(id);
    }

    /**
     * This method will return a view that use is able to register a new account from.
     */
    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String registerSocialAccount(ModelMap model) {

        return "/social/socialRegister";
    }

    /**
     * This method will refresh all social media data and create
     * new Interactions and score for Sentiment.
     */
    @RequestMapping(value = { "/refresh" }, method = RequestMethod.GET)
    public String refreshSocialData(ModelMap model) {

        interactionService.addInteractionsFromDataProviders();
        sentimentService.getSentiment();

        return "redirect:/interaction/list";
    }

    /**
     * This method builds a URL for an endpoint
     * @param request the request object
     * @param endPoint the end point to build a URL for.
     * @return the request URL based on the request object.
     */
    private String buildUrl(HttpServletRequest request, String endPoint) {
        final int serverPort = request.getServerPort();
        String url = "";
        if ((serverPort == 80) || (serverPort == 443)) {
            // No need to add the server port for standard HTTP and HTTPS ports, the scheme will help determine it.
            url = String.format("%s://%s/%s", request.getScheme(), request.getServerName(), endPoint);
        } else {
            url = String.format("%s://%s:%s/%s", request.getScheme(), request.getServerName(), serverPort, endPoint);
        }

        return url;
    }
}
