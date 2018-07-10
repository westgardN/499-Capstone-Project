package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.service.InteractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * The InteractionController is responsible for handling all Interaction requests.
 */
@Controller
public class InteractionController {

    static final Logger logger = LoggerFactory.getLogger(SocialNetworkController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    InteractionService interactionService;

    /**
     * This method will list all existing interactions.
     */
    @RequestMapping(value = { "/interaction", "/interaction/list" }, method = RequestMethod.GET)
    public ModelAndView listRegisteredAccounts(ModelMap model) {

        List<Interaction> interactions = interactionService.findAll();
        return new ModelAndView("/interaction/interactionList", "interactions", interactions);
    }

}
