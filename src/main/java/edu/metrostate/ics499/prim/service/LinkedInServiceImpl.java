package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.*;
import edu.metrostate.ics499.prim.templates.PrimLinkedInTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.api.*;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

/**
 * The LinkedInServiceImpl is a Spring Social based implementation of the
 * LinkedInService interface.
 */
@Service("linkedInService")
public class LinkedInServiceImpl implements LinkedInService {
	@Value("${spring.social.linkedIn.clientId}")
	String linkedInAppId;

	@Value("${spring.social.linkedIn.clientSecret}")
	String linkedInSecret;

	@Value("${spring.social.linkedIn.permissions}")
	String linkedInPermissions;

	@Value("${spring.social.linkedIn.authUri}")
	String linkedInAuthUri;

	@Autowired
	SocialNetworkRegistrationService socialNetworkRegistrationService;

	/**
	 * Builds the LinkedIn Authorization URL for use with OAth2. This URL is
	 * returned to the application so that the user can authorize access to our
	 * app. This method should be called prior to calling registerLinkedIn
	 *
	 * @return The authorization URL to pass to the client.
	 */
	@Override
	public String buildAuthorizationUrl() {
		LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedInAppId, linkedInSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(linkedInAuthUri);
		params.setScope(linkedInPermissions);
		params.setState(connectionFactory.generateState());

		return oauthOperations.buildAuthorizeUrl(params);
	}

	/**
	 * Registers LinkedIn using the specified verification code.
	 *
	 * @param code
	 *            the verification code received from the client request.
	 */
	@Override
	@Transactional
	public void registerLinkedIn(String code) {
		List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
				.findNonExpiredBySocialNetwork(SocialNetwork.LINKEDIN);

		LinkedInConnectionFactory connectionFactory = new LinkedInConnectionFactory(linkedInAppId, linkedInSecret);
		AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, linkedInAuthUri, null);
		LinkedIn liNew = new LinkedInTemplate(accessGrant.getAccessToken());
        String name = liNew.profileOperations().getUserProfile().getLastName() + ", " + liNew.profileOperations().getUserProfile().getFirstName();
		String idNew = liNew.profileOperations().getProfileId();

		if (socialNetworkRegistrationList.isEmpty()) {
			socialNetworkRegistrationService.register(SocialNetwork.LINKEDIN, accessGrant, name);
		} else {
			Date now = new Date();
			boolean found = false;

			for (int i = 0; i < socialNetworkRegistrationList.size(); i++) {
				SocialNetworkRegistration socialNetworkRegistration = socialNetworkRegistrationList.get(i);

				LinkedIn liCurrent = new LinkedInTemplate(socialNetworkRegistration.getToken());

				String idCurrent = liCurrent.profileOperations().getProfileId();

				if (Objects.equals(idCurrent, idNew) == true) {
					found = true;
					socialNetworkRegistration.setToken(accessGrant.getAccessToken());
					socialNetworkRegistration.setRefreshToken(accessGrant.getRefreshToken());
					socialNetworkRegistration.setExpires(new Date(accessGrant.getExpireTime()));
					socialNetworkRegistration.setLastUsed(now);
					break;
				}
			}

			if (!found) {
				socialNetworkRegistrationService.register(SocialNetwork.LINKEDIN, accessGrant, name);
			}
		}
	}

	/**
	 * Refreshes the specified SocialNetworkRegistration token.
	 *
	 * @param socialNetworkRegistration
	 *            the registration to refresh authorization token to for.
	 */
	@Transactional
	@Override
	public void refreshToken(SocialNetworkRegistration socialNetworkRegistration) {
//		UriComponents uri = UriComponentsBuilder.fromPath(linkedInRefreshTokenPath).scheme(linkedInScheme)
//				.host(linkedInHost).queryParam(CLIENT_ID, linkedInAppId).queryParam(CLIENT_SECRET, linkedInSecret)
//				.queryParam(GRANT_TYPE, linkedInGrantType)
//				.queryParam(LI_EXCHANGE_TOKEN, socialNetworkRegistration.getToken()).build();
//
//		String url = uri.toString();
//
//		LinkedIn linkedIn = new LinkedInTemplate(socialNetworkRegistration.getToken());
//
//		ResponseEntity<String> exchange = linkedIn.restOperations().exchange(url, HttpMethod.GET, HttpEntity.EMPTY,
//				String.class);
//
//		if (exchange.getStatusCode().is2xxSuccessful() == true) {
//			try {
//				LinkedInRefreshTokenResponse linkedInRefreshTokenResponse = new ObjectMapper()
//						.readValue(exchange.getBody(), LinkedInRefreshTokenResponse.class);
//
//				// Update the expiration.
//				Instant timestamp = new Date().toInstant();
//				socialNetworkRegistration
//						.setExpires(Date.from(timestamp.plusSeconds(linkedInRefreshTokenResponse.getExpires_in())));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	/**
	 * Returns true if a non-expired LinkedIn registration exists
	 *
	 * @return true if a non-expired LinkedIn registration exists
	 */
	@Override
	public boolean isRegistered() {
		boolean result = false;

		if (socialNetworkRegistrationService.isRegistered(SocialNetwork.LINKEDIN) == true) {
			result = true;
		}

		return result;
	}

	/**
	 * Returns the authorized LinkedIn object.
	 *
	 * @return the authorized LinkedIn object.
	 */
	@Transactional
	@Override
	public LinkedIn getLinkedIn() {
		List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
				.findBySocialNetwork(SocialNetwork.LINKEDIN);

		LinkedIn linkedIn = null;

		for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
			linkedIn = new LinkedInTemplate(socialNetworkRegistration.getToken());
			break;
		}

		return linkedIn;
	}

	/**
	 * Returns a list of all non-expired LinkedIn instances.
	 */
	@Override
	public List<LinkedIn> getAllNonExpiredLinkedIns() {
		List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
				.findBySocialNetwork(SocialNetwork.LINKEDIN);

		List<LinkedIn> linkedIns = new LinkedList<>();

		for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
			if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
				refreshToken(socialNetworkRegistration);
				socialNetworkRegistrationService.update(socialNetworkRegistration);
			}

			if (!socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
				linkedIns.add(new LinkedInTemplate(socialNetworkRegistration.getToken()));
			}
		}

		return linkedIns;
	}

	/**
	 * Returns a list of all supported post types from the specified accounts
	 *
	 * @param linkedIn
	 *            the LinkedIn account to retrieve the data from.
	 * @return a list of all supported post types from the specified account.
	 */
	@Override
	public List<Post> getAllPostTypeItems(PrimLinkedInTemplate linkedIn) {
		List<Post> posts = new LinkedList<>();

		String profileId = linkedIn.profileOperations().getProfileId();

		LinkedInProfile linkedInProfile = linkedIn.profileOperations().getUserProfile();
        LinkedInProfileFull linkedInProfileFull = linkedIn.profileOperations().getUserProfileFull();

		List<LinkedInCompany> companies = linkedIn.getCompaniesMemberIsAdministratorOf();

		for (LinkedInCompany linkedInCompany : companies) {
            //linkedIn.companyOperations().
//		    Company company = linkedIn.companyOperations().getCompany(linkedInCompany.getId());

//            final List<LinkedInNetworkUpdate> networkUpdates = linkedIn.networkUpdateOperations().getNetworkUpdates();
        }
//
//		for (Post post : linkedIn.feedOperations().getFeed()) {
//			posts.add(post);
//		}
//
//		for (Post post : linkedIn.feedOperations().getTagged()) {
//			posts.add(post);
//		}

		return posts;
	}

	/**
	 * Returns a list of all supported post types from the authenticated
	 * accounts.
	 *
	 * @return a list of all supported post types from the authenticated
	 *         accounts.
	 */
	@Transactional
	@Override
	public List<Post> getAllPostTypeItems() {
		List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
				.findBySocialNetwork(SocialNetwork.LINKEDIN);

        PrimLinkedInTemplate linkedIn = null;
		List<Post> posts = new LinkedList<>();

		for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
			if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
				refreshToken(socialNetworkRegistration);
			}

			linkedIn = new PrimLinkedInTemplate(socialNetworkRegistration.getToken());

			posts.addAll(getAllPostTypeItems(linkedIn));
			socialNetworkRegistration.setLastUsed(new Date());
		}

		return posts;
	}

	/**
	 * Returns a List of Interactions or an empty list if there is no data.
	 *
	 * @return a List of Interactions or an empty list if there is no data.
	 */
	@Override
	public List<Interaction> getInteractions() {
		List<Interaction> interactions = new ArrayList<>();

		// Get the LinkedIn Feed data.
		for (Post post : getAllPostTypeItems()) {
			Interaction interaction = new Interaction();

			Date createdTime = post.getCreationTimestamp();
			interaction.setCreatedTime(createdTime != null ? createdTime : new Date());

			interaction.setDescription(post.getSummary());
			interaction.setFlag(InteractionFlag.NEW);

			final LinkedInProfile from = post.getCreator();

			if (from != null) {
				interaction.setFromId(post.getCreator().getId());
				interaction.setFromName(post.getCreator().getFirstName());
			}

			interaction.setMessageId(post.getId());
			interaction.setMessage(post.getSummary());
			//interaction.setMessageLink(post.);
			interaction.setSocialNetwork(SocialNetwork.LINKEDIN);
			interaction.setState(InteractionState.OPEN);
			interaction.setType(getType(post));

			interactions.add(interaction);
		}

		return interactions;
	}

	private InteractionType getType(Post post) {
		Post.PostType postType = post.getType();

		InteractionType interactionType = null;

		if (postType != null) {
			interactionType = InteractionType.valueOf(postType.name());
		}

		return interactionType;
	}

	/**
	 * Nested class used soley to convert the refresh token response body from
	 * JSON to Java.
	 *
	 * Used by Jackson API to convert the JSON response to an instance of this
	 * type.
	 */
	static private class LinkedInRefreshTokenResponse implements Serializable {
		/**
		 * The access token that was refreshed.
		 */
		private String access_token;

		/**
		 * The type of the token
		 */
		private String token_type;

		/**
		 * The number of seconds from now that the token will expire.
		 */
		private Long expires_in;

		public LinkedInRefreshTokenResponse() {
			this(null, null, 0L);
		}

		public LinkedInRefreshTokenResponse(String access_token, String token_type, Long expires_in) {
			this.access_token = access_token;
			this.token_type = token_type;
			this.expires_in = expires_in;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getToken_type() {
			return token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public Long getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(Long expires_in) {
			this.expires_in = expires_in;
		}
	}
}
