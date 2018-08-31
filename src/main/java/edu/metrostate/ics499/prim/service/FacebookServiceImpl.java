package edu.metrostate.ics499.prim.service;

import com.google.common.base.Strings;
import edu.metrostate.ics499.prim.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Action;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

/**
 * The FacebookServiceImpl is a Spring Social based implementation of the FacebookService interface.
 */
@Service("facebookService")
public class FacebookServiceImpl implements FacebookService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String FB_EXCHANGE_TOKEN = "fb_exchange_token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String PRIM_NAMESPACE = "primnamespace";

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;

    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;

    @Value("${spring.social.facebook.authUri}")
    String facebookAuthUri;

    @Value("${spring.social.facebook.refreshTokenPath}")
    String facebookRefreshTokenPath;

    @Value("${spring.social.facebook.scheme}")
    String facebookScheme;

    @Value("${spring.social.facebook.host}")
    String facebookHost;

    @Value("${spring.social.facebook.grantType}")
    String facebookGrantType;

    @Value("${spring.social.facebook.permissions}")
    String facebookPermissions;

    @Autowired
    SocialNetworkRegistrationService socialNetworkRegistrationService;

    /**
     * Builds the Facebook Authorization URL for use with OAth2.
     * This URL is returned to the application so that the user can authorize access to our app.
     * This method should be called prior to calling registerFacebook
     *
     * @return The authorization URL to pass to the client.
     */
    @Override
    public String buildAuthorizationUrl() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(facebookAuthUri);
        params.setScope(facebookPermissions);

        return oauthOperations.buildAuthorizeUrl(params);
    }

    /**
     *  Registers Facebook using the specified verification code.
     *
     * @param code the verification code received from the client request.
     */
    @Override
    @Transactional
    public void registerFacebook(String code) {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService.findNonExpiredBySocialNetwork(SocialNetwork.FACEBOOK);

        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, facebookAuthUri, null);
        String[] fields = {"name"};
        Facebook facebook = new FacebookTemplate(accessGrant.getAccessToken(), PRIM_NAMESPACE);
        String[] names = facebook.fetchObject("me", String.class, fields).split("\\,");
        String name = names[0].split("\\:")[1].replace("\"", "");

        if (socialNetworkRegistrationList.isEmpty()) {
            socialNetworkRegistrationService.register(SocialNetwork.FACEBOOK, accessGrant, name);
        } else {
            Date now = new Date();
            boolean found = false;


            for (int i = 0; i < socialNetworkRegistrationList.size(); i++) {
                SocialNetworkRegistration socialNetworkRegistration = socialNetworkRegistrationList.get(i);

                Facebook fbCurrent = new FacebookTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);
                String idCurrent = fbCurrent.fetchObject("me", String.class, fields);

                if (Objects.equals(idCurrent, name) == true) {
                    found = true;
                    socialNetworkRegistration.setToken(accessGrant.getAccessToken());
                    socialNetworkRegistration.setRefreshToken(accessGrant.getRefreshToken());
                    socialNetworkRegistration.setExpires(new Date(accessGrant.getExpireTime()));
                    socialNetworkRegistration.setLastUsed(now);
                    break;
                }
            }

            if (!found) {
                socialNetworkRegistrationService.register(SocialNetwork.FACEBOOK, accessGrant, name);
            }
        }
    }

    /**
     * Refreshes the specified SocialNetworkRegistration token.
     *
     * @param socialNetworkRegistration the registration to refresh authorization token to for.
     */
    @Transactional
    @Override
    public void refreshToken(SocialNetworkRegistration socialNetworkRegistration) {
        UriComponents uri = UriComponentsBuilder
                .fromPath(facebookRefreshTokenPath)
                .scheme(facebookScheme)
                .host(facebookHost)
                .queryParam(CLIENT_ID, facebookAppId)
                .queryParam(CLIENT_SECRET, facebookSecret)
                .queryParam(GRANT_TYPE, facebookGrantType)
                .queryParam(FB_EXCHANGE_TOKEN, socialNetworkRegistration.getToken())
                .build();

        String url = uri.toString();

        Facebook facebook = new FacebookTemplate(socialNetworkRegistration.getToken());

        ResponseEntity<String> exchange = facebook.restOperations()
                .exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        if (exchange.getStatusCode().is2xxSuccessful() == true) {
            try {
                FacebookRefreshTokenResponse facebookRefreshTokenResponse = new ObjectMapper().readValue(exchange.getBody(), FacebookRefreshTokenResponse.class);

                // Update the expiration.
                Instant timestamp = new Date().toInstant();
                socialNetworkRegistration.setExpires(Date.from(timestamp.plusSeconds(facebookRefreshTokenResponse.getExpires_in())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns true if a non-expired Facebook registration exists
     *
     * @return true if a non-expired Facebook registration exists
     */
    @Override
    public boolean isRegistered() {
        boolean result = false;

        if (socialNetworkRegistrationService.isRegistered(SocialNetwork.FACEBOOK) == true) {
            result = true;
        }

        return result;
    }

    /**
     * Returns the authorized Facebook object.
     *
     * @return the authorized Facebook object.
     */
    @Transactional
    @Override
    public Facebook getFaceBook() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.FACEBOOK);

        Facebook facebook = null;

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            facebook = new FacebookTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);
            break;
        }

        return facebook;
    }

    /**
     * Returns a list of all non-expired Facebook instances.
     */
    @Override
    public List<Facebook> getAllNonExpiredFacebooks() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.FACEBOOK);

        List<Facebook> facebooks = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
                socialNetworkRegistrationService.update(socialNetworkRegistration);
            }

            if (!socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                facebooks.add(new FacebookTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE));
            }
        }

        return facebooks;
    }

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param facebook the Facebook account to retrieve the data from.
     * @return a list of all supported post types from the specified account.
     */
    @Override
    public List<Post> getAllPostTypeItems(Facebook facebook) {
        List<Post> posts = new LinkedList<>();

        for (Post post : facebook.feedOperations().getFeed()) {
            addPost(posts, post);
        }

        for (Post post : facebook.feedOperations().getTagged()) {
            addPost(posts, post);
        }

        return posts;
    }

    private void addPost(List<Post> posts, Post post) {
        InteractionType type = getType(post);
        if (!Strings.isNullOrEmpty(post.getMessage())
                && (type == InteractionType.LINK || type == InteractionType.STATUS)) {
            posts.add(post);
        }
    }

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    @Transactional
    @Override
    public List<Post> getAllPostTypeItems() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.FACEBOOK);

        Facebook facebook = null;
        List<Post> posts = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            facebook = new FacebookTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);

            posts.addAll(getAllPostTypeItems(facebook));
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

        // Get the Facebook Feed data.
        for (Post post : getAllPostTypeItems()) {
            Interaction interaction = new Interaction();

            Date createdTime = post.getCreatedTime();
            interaction.setCreatedTime(createdTime != null ? createdTime : new Date());

            interaction.setDescription(post.getDescription());
            interaction.setFlag(InteractionFlag.NEW);

            final Reference from = post.getFrom();

            if (from != null) {
                interaction.setFromId(post.getFrom().getId());
                interaction.setFromName(post.getFrom().getName());
            }

            interaction.setMessageId(post.getId());
            interaction.setMessage(post.getMessage());
            List<Action> actions = post.getActions();

            String linkComment = post.getLink();

            for (Action action : actions) {
                if (action.getName().equalsIgnoreCase("comment") == true) {
                    linkComment = action.getLink();
                }
            }
            interaction.setMessageLink(linkComment);
            interaction.setSocialNetwork(SocialNetwork.FACEBOOK);
            interaction.setState(InteractionState.OPEN);
            interaction.setType(getType(post));

            interactions.add(interaction);
        }

        return interactions;
    }

    private InteractionType getType(Post post) {
        Post.PostType postType = post.getType();

        InteractionType interactionType = InteractionType.UNKNOWN;

        if (postType != null) {
            interactionType = InteractionType.valueOf(postType.name());
        }

        return interactionType;
    }

    /**
     * Nested class used soley to convert the refresh token response body from JSON to Java.
     *
     * Used by Jackson API to convert the JSON response to an instance of this type.
     */
    static private class FacebookRefreshTokenResponse implements Serializable {
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

        public FacebookRefreshTokenResponse() {
            this(null, null, 0L);
        }

        public FacebookRefreshTokenResponse(String access_token, String token_type, Long expires_in) {
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
