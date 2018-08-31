package edu.metrostate.ics499.prim.service;
import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import edu.metrostate.ics499.prim.templates.PrimLinkedInTemplate;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.Post;
import java.util.List;

/**
 * This class provides an interface for registering with LinkedIn and retrieving data from LinkedIn.
 * @author Paty
 *
 */
public interface LinkedInService extends InteractionProvider {

	    /**
	     * Builds the LinkedIn Authorization URL for use with OAth2.
	     * This URL is returned to the application so that the user can authorize access to our app.
	     *
	     * @return The authorization URL to pass to the client.
	     */
	    String buildAuthorizationUrl();

	    /**
	     *  Registers LinkedIn using the specified verification code.
	     *
	     * @param code the verification code received from the client request.
	     */
	    void registerLinkedIn(String code);

	    /**
	     * Refreshes the specified SocialNetworkRegistration token.
	     *
	     * @param socialNetworkRegistration the registration to refresh authorization token to for.
	     */
	    void refreshToken(SocialNetworkRegistration socialNetworkRegistration);

	    /**
	     * Returns true if a non-expired LinkedIn registration exists
	     *
	     * @return true if a non-expired LinkedIn registration exists
	     */
	    boolean isRegistered();

	    /**
	     * Returns the authorized LinkedIn object.
	     *
	     * @return the authorized LinkedIn object.
	     */
	    LinkedIn getLinkedIn();

	    /**
	     * Returns a list of all supported post types from the specified accounts
	     *
	     * @param linkedIn the LinkedIn account to retrieve the data from.
	     *
	     * @return a list of all supported post types from the specified account.
	     */
	    List<Post> getAllPostTypeItems(PrimLinkedInTemplate linkedIn);

	    /**
	     * Returns a list of all supported post types from the authenticated accounts.
	     *
	     * @return a list of all supported post types from the authenticated accounts.
	     */
	    List<Post> getAllPostTypeItems();

	    /**
	     * Returns a list of all non-expired LinkedIn instances.
	     */
	    List<LinkedIn> getAllNonExpiredLinkedIns();
	}


