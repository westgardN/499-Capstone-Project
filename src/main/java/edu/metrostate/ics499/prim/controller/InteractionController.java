package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.service.InteractionService;
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
 * The InteractionController is responsible for handling all Interaction requests.
 */
@Controller
@RequestMapping("/interaction")
public class InteractionController {

    static final Logger logger = LoggerFactory.getLogger(SocialNetworkController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    InteractionService interactionService;

    /**
     * This method will list all Open interactions.
     */
    @RequestMapping(value = { "", "/", "/list" }, method = RequestMethod.GET)
    public ModelAndView openInteractions(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<Interaction> interactions = interactionService.findAllOpen();
        ModelAndView mav = new ModelAndView("/interaction/interactionList", "interactions", interactions);
        mav.getModelMap().addAttribute("closeUrl", buildUrl(request, "interaction/close/"));
        mav.getModelMap().addAttribute("ignoreUrl", buildUrl(request, "interaction/ignore/"));
        mav.getModelMap().addAttribute("followUpUrl", buildUrl(request, "interaction/defer/"));
        mav.getModelMap().addAttribute("replyUrl", buildUrl(request, "interaction/reply/"));
        return mav;
    }

    /**
     * This method will list all Ignored / Deleted interactions.
     */
    @RequestMapping(value = { "/ignored" }, method = RequestMethod.GET)
    public ModelAndView ignoredInteractions(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<Interaction> interactions = interactionService.findAllDeleted();
        ModelAndView mav = new ModelAndView("/interaction/ignoredList", "interactions", interactions);
        mav.getModelMap().addAttribute("closeUrl", buildUrl(request, "interaction/close/"));
        mav.getModelMap().addAttribute("reopenUrl", buildUrl(request, "interaction/reopen/"));
        mav.getModelMap().addAttribute("followUpUrl", buildUrl(request, "interaction/defer/"));
        mav.getModelMap().addAttribute("replyUrl", buildUrl(request, "interaction/reply/"));
        return mav;
    }

    /**
     * This method will list all Deferred / Follow-up interactions.
     */
    @RequestMapping(value = { "/deferred" }, method = RequestMethod.GET)
    public ModelAndView deferredInteractions(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<Interaction> interactions = interactionService.findAllDeferred();
        ModelAndView mav = new ModelAndView("/interaction/deferredList", "interactions", interactions);
        mav.getModelMap().addAttribute("closeUrl", buildUrl(request, "interaction/close/"));
        mav.getModelMap().addAttribute("reopenUrl", buildUrl(request, "interaction/reopen/"));
        mav.getModelMap().addAttribute("ignoreUrl", buildUrl(request, "interaction/ignore/"));
        mav.getModelMap().addAttribute("replyUrl", buildUrl(request, "interaction/reply/"));
        return mav;
    }

    /**
     * This method will list all Deferred / Follow-up interactions.
     */
    @RequestMapping(value = { "/closed" }, method = RequestMethod.GET)
    public ModelAndView closedInteractions(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        List<Interaction> interactions = interactionService.findAllClosed();
        ModelAndView mav = new ModelAndView("/interaction/closedList", "interactions", interactions);
        mav.getModelMap().addAttribute("reopenUrl", buildUrl(request, "interaction/reopen/"));
        mav.getModelMap().addAttribute("ignoreUrl", buildUrl(request, "interaction/ignore/"));
        mav.getModelMap().addAttribute("followUpUrl", buildUrl(request, "interaction/defer/"));
        mav.getModelMap().addAttribute("replyUrl", buildUrl(request, "interaction/reply/"));
        return mav;
    }

    @ResponseBody
    @RequestMapping(value =  { "/close/{id}" }, method = RequestMethod.POST)
    public void closeInteraction(@PathVariable("id") int id, HttpServletRequest request) {
        interactionService.closeById(id);
    }

    @ResponseBody
    @RequestMapping(value =  { "/ignore/{id}" }, method = RequestMethod.POST)
    public void ignoreInteraction(@PathVariable("id") int id, HttpServletRequest request) {
        interactionService.ignoreById(id);
    }

    @ResponseBody
    @RequestMapping(value =  { "/defer/{id}" }, method = RequestMethod.POST)
    public void followUpInteraction(@PathVariable("id") int id, HttpServletRequest request) {
        interactionService.deferById(id);
    }

    @ResponseBody
    @RequestMapping(value =  { "/reopen/{id}" }, method = RequestMethod.POST)
    public void reopenInteraction(@PathVariable("id") int id, HttpServletRequest request) {
        interactionService.reopenById(id);
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
