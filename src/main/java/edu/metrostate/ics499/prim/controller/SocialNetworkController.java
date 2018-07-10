package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.service.InteractionService;
import edu.metrostate.ics499.prim.service.SocialNetworkRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The SocialNetworkController class handles all social networking requests
 * for PRIM
 */
@Controller
public class SocialNetworkController {

    static final Logger logger = LoggerFactory.getLogger(SocialNetworkController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    SocialNetworkRegistrationService socialNetworkRegistrationService;

    @Autowired
    InteractionService interactionService;

    /**
     * This method will list all existing registered social accounts.
     */
    @RequestMapping(value = { "/social", "/social/list" }, method = RequestMethod.GET)
    public ModelAndView listRegisteredAccounts(ModelMap model) {

        List<SocialNetworkRegistration> accounts = socialNetworkRegistrationService.findAll();
        return new ModelAndView("/social/socialList", "accounts", accounts);
    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/social/register" }, method = RequestMethod.GET)
    public String registerSocialAccount(ModelMap model) {

        return "/social/socialRegister";
    }

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/social/refresh" }, method = RequestMethod.GET)
    public String refreshSocialData(ModelMap model) {

        interactionService.addInteractionsFromDataProviders();

        return "redirect:/interaction/list";
    }

}
