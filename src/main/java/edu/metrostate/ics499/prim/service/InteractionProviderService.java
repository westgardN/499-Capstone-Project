package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.provider.InteractionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("interactionProviderService")
@Transactional
public class InteractionProviderService {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private LinkedInService linkedInService;

    public List<InteractionProvider> getAllProviders() {
        List<InteractionProvider> interactionProviders = new ArrayList<>();

        interactionProviders.add(facebookService);
        interactionProviders.add(twitterService);
        interactionProviders.add(linkedInService);

        return interactionProviders;
    }
}
