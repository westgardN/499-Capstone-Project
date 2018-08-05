package edu.metrostate.ics499.prim.controller;

import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    InteractionService interactionService;

    /**
     * Generates the Facebook authorization URL that the client is redirected to.
     *
     * @return the URL to redirect the client to.
     * @throws IOException
     */
    @GetMapping("/interactionCountBySocialNetwork")
    public Map<SocialNetwork, Long> createFacebookAuthorizationUrl() throws IOException {
        return interactionService.interactionCountBySocialNetwork();
    }


}
