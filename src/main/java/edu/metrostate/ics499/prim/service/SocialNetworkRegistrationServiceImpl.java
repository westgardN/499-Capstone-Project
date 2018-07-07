package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.repository.SocialNetworkRegistrationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("socialNetworkRegistrationService")
@Transactional
public class SocialNetworkRegistrationServiceImpl implements SocialNetworkRegistrationService {

    @Autowired
    private SocialNetworkRegistrationDao dao;

    /**
     * Returns a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     *
     * @param id The Id of the SocialNetworkRegistration to retrieve.
     * @return a persistent SocialNetworkRegistration object identified by the specified id.
     * If no SocialNetworkRegistration with that id exists, null is returned.
     */
    @Override
    public SocialNetworkRegistration findById(int id) {
        return dao.findById(id);
    }

    /**
     * Returns a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     *
     * @param socialNetwork The Social Network to retrieve a list of SocialNetworkRegistrations for.
     * @return a List of persistent SocialNetworkRegistrations for the specified Social Network.
     * If no SocialNetworkRegistrations exist, an empty List is returned.
     */
    @Override
    public List<SocialNetworkRegistration> findBySocialNetwork(String socialNetwork) {
        return dao.findBySocialNetwork(socialNetwork);
    }

    /**
     * Returns a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     *
     * @return a List of all persistent SocialNetworkRegistrations. If no SocialNetworkRegistrations exist,
     * an empty List is returned.
     */
    @Override
    public List<SocialNetworkRegistration> findAll() {
        return dao.findAll();
    }

    /**
     * Immediately saves the specified SocialNetworkRegistration to the backing store.
     *
     * @param socialNetworkRegistration the SocialNetworkRegistration to save.
     */
    @Override
    public void save(SocialNetworkRegistration socialNetworkRegistration) {
        dao.save(socialNetworkRegistration);
    }

    /**
     * Updates the persistent SocialNetworkRegistration based on the specified SocialNetworkRegistration.
     *
     * @param socialNetworkRegistration the SocialNetworkRegistration to update.
     */
    @Override
    public void update(SocialNetworkRegistration socialNetworkRegistration) {
        SocialNetworkRegistration entity = dao.findById(socialNetworkRegistration.getId());

        if (entity != null) {
            entity.setSocialNetwork(socialNetworkRegistration.getSocialNetwork());
            entity.setExpires(socialNetworkRegistration.getExpires());
            entity.setLastUsed(socialNetworkRegistration.getLastUsed());
            entity.setRefreshToken(socialNetworkRegistration.getRefreshToken());
            entity.setToken(socialNetworkRegistration.getToken());
        }
    }

    /**
     * Deletes the specified SocialNetworkRegistration from the backing store.
     *
     * @param id the id of the SocialNetworkRegistration to delete.
     */
    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    /**
     * Deletes all SocialNetworkRegistrations from the backing store for the specified Social Network.
     *
     * @param socialNetwork the Social Network to delete registrations for.
     */
    @Override
    public void deleteBySocialNetwork(String socialNetwork) {
        dao.deleteBySocialNetwork(socialNetwork);
    }
}
