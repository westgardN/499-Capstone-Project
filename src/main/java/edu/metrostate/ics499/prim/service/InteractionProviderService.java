package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.provider.InteractionProvider;
import edu.metrostate.ics499.prim.service.FacebookService;
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

    public List<InteractionProvider> getAllProviders() {
        List<InteractionProvider> interactionProviders = new ArrayList<>();

        interactionProviders.add(facebookService);

        return interactionProviders;
    }
}
