package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.*;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

/**
 * The TwitterServiceImpl is a Spring Social based implementation of the TwitterService interface.
 */
@Service("twitterService")
public class TwitterServiceImpl implements TwitterService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String FB_EXCHANGE_TOKEN = "fb_exchange_token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String PRIM_NAMESPACE = "primnamespace";

    @Value("${spring.social.twitter.appId}")
    String twitterAppId;

    @Value("${spring.social.twitter.appSecret}")
    String twitterSecret;

    @Value("${spring.social.twitter.authUri}")
    String twitterAuthUri;

    @Value("${spring.social.twitter.refreshTokenPath}")
    String twitterRefreshTokenPath;

    @Value("${spring.social.twitter.scheme}")
    String twitterScheme;

    @Value("${spring.social.twitter.host}")
    String twitterHost;

    @Value("${spring.social.twitter.grantType}")
    String twitterGrantType;

    @Value("${spring.social.twitter.permissions}")
    String twitterPermissions;

    @Autowired
    SocialNetworkRegistrationService socialNetworkRegistrationService;

    /**
     * Builds the Twitter Authorization URL for use with OAth2.
     * This URL is returned to the application so that the user can authorize access to our app.
     * This method should be called prior to calling registerTwitter
     *
     * @return The authorization URL to pass to the client.
     */
    @Override
    public String buildAuthorizationUrl() {
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterAppId, twitterSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri(twitterAuthUri);
        params.setScope(twitterPermissions);

        return oauthOperations.buildAuthorizeUrl(params);
    }

    /**
     *  Registers Twitter using the specified verification code.
     *
     * @param code the verification code received from the client request.
     */
    @Override
    @Transactional
    public void registerTwitter(String code) {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService.findNonExpiredBySocialNetwork(SocialNetwork.TWITTER);

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterAppId, twitterSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, twitterAuthUri, null);

        if (socialNetworkRegistrationList.isEmpty()) {
            socialNetworkRegistrationService.register(SocialNetwork.TWITTER, accessGrant);
        } else {
            Date now = new Date();
            boolean found = false;

            String[] fields = {"id", "name"};
            for (int i = 0; i < socialNetworkRegistrationList.size(); i++) {
                SocialNetworkRegistration socialNetworkRegistration = socialNetworkRegistrationList.get(i);

                Twitter fbCurrent = new TwitterTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);
                Twitter fbNew = new TwitterTemplate(accessGrant.getAccessToken(), PRIM_NAMESPACE);
                String idCurrent = fbCurrent.fetchObject("me", String.class, fields);
                String idNew = fbNew.fetchObject("me", String.class, fields);

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
                socialNetworkRegistrationService.register(SocialNetwork.TWITTER, accessGrant);
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
                .fromPath(twitterRefreshTokenPath)
                .scheme(twitterScheme)
                .host(twitterHost)
                .queryParam(CLIENT_ID, twitterAppId)
                .queryParam(CLIENT_SECRET, twitterSecret)
                .queryParam(GRANT_TYPE, twitterGrantType)
                .queryParam(FB_EXCHANGE_TOKEN, socialNetworkRegistration.getToken())
                .build();

        String url = uri.toString();

        Twitter twitter = new TwitterTemplate(socialNetworkRegistration.getToken());

        ResponseEntity<String> exchange = twitter.restOperations()
                .exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        if (exchange.getStatusCode().is2xxSuccessful() == true) {
            try {
                TwitterRefreshTokenResponse twitterRefreshTokenResponse = new ObjectMapper().readValue(exchange.getBody(), TwitterRefreshTokenResponse.class);

                // Update the expiration.
                Instant timestamp = new Date().toInstant();
                socialNetworkRegistration.setExpires(Date.from(timestamp.plusSeconds(twitterRefreshTokenResponse.getExpires_in())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns true if a non-expired Twitter registration exists
     *
     * @return true if a non-expired Twitter registration exists
     */
    @Override
    public boolean isRegistered() {
        boolean result = false;

        if (socialNetworkRegistrationService.isRegistered(SocialNetwork.TWITTER) == true) {
            result = true;
        }

        return result;
    }

    /**
     * Returns the authorized Twitter object.
     *
     * @return the authorized Twitter object.
     */
    @Transactional
    @Override
    public Twitter getTwitter() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            twitter = new TwitterTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);
            break;
        }

        return twitter;
    }

    /**
     * Returns a list of all non-expired Twitter instances.
     */
    @Override
    public List<Twitter> getAllNonExpiredTwitters() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;
        List<Twitter> twitters = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
                socialNetworkRegistrationService.update(socialNetworkRegistration);
            }

            if (!socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                twitters.add(new TwitterTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE));
            }
        }

        return twitters;
    }

    /**
     * Returns a list of all supported post types from the specified accounts
     *
     * @param twitter the Twitter account to retrieve the data from.
     * @return a list of all supported post types from the specified account.
     */
    @Override
    public List<Tweet> getAllTweetTypeItems(Twitter twitter) {
        List<Tweet> posts = new LinkedList<>();

        posts.addAll(twitter.feedOperations().getFeed());
        posts.addAll(twitter.feedOperations().getTagged());

        return posts;
    }

    /**
     * Returns a list of all supported post types from the authenticated accounts.
     *
     * @return a list of all supported post types from the authenticated accounts.
     */
    @Transactional
    @Override
    public List<Tweet> getAllTweetTypeItems() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;
        List<Tweet> posts = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            twitter = new TwitterTemplate(socialNetworkRegistration.getToken(), PRIM_NAMESPACE);

            posts.addAll(getAllTweetTypeItems(twitter));
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

        // Get the Twitter Feed data.
        for (Tweet tweet : getAllTweetTypeItems()) {
            Interaction interaction = new Interaction();

            Date createdTime = tweet.getCreatedTime();
            interaction.setCreatedTime(createdTime != null ? createdTime : new Date());

            interaction.setDescription(tweet.getDescription());
            interaction.setFlag(InteractionFlag.NEW);

            final Reference from = tweet.getFrom();

            if (from != null) {
                interaction.setFromId(tweet.getFrom().getId());
                interaction.setFromName(tweet.getFrom().getName());
            }

            interaction.setMessageId(tweet.getId());
            interaction.setMessage(tweet.getMessage());
            interaction.setMessageLink(tweet.getLink());
            interaction.setSocialNetwork(SocialNetwork.TWITTER);
            interaction.setState(InteractionState.OPEN);
            interaction.setType(getType(tweet));

            interactions.add(interaction);
        }

        return interactions;
    }

    private InteractionType getType(Tweet tweet) {
        Post.PostType postType = tweet.getType();

        InteractionType interactionType = null;

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
    static private class TwitterRefreshTokenResponse implements Serializable {
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

        public TwitterRefreshTokenResponse() {
            this(null, null, 0L);
        }

        public TwitterRefreshTokenResponse(String access_token, String token_type, Long expires_in) {
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
