package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SocialNetwork;
import edu.metrostate.ics499.prim.model.SocialNetworkRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service("facebookService")
public class FacebookServiceImpl implements FacebookService {
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String FB_EXCHANGE_TOKEN = "fb_exchange_token";
    private static final String GRANT_TYPE = "grant_type";
    private static final String SCHEME = "https";

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

        if (socialNetworkRegistrationList.isEmpty()) {
            socialNetworkRegistrationService.register(SocialNetwork.FACEBOOK, accessGrant);
        } else {
            Date now = new Date();
            boolean found = false;

            String[] fields = {"id", "name"};
            for (int i = 0; i < socialNetworkRegistrationList.size(); i++) {
                SocialNetworkRegistration socialNetworkRegistration = socialNetworkRegistrationList.get(i);

                Facebook fbCurrent = new FacebookTemplate(socialNetworkRegistration.getToken());
                Facebook fbNew = new FacebookTemplate(accessGrant.getAccessToken());
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
                socialNetworkRegistrationService.register(SocialNetwork.FACEBOOK, accessGrant);
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
                FacebookRefreshTokenResposne facebookRefreshTokenResposne = new ObjectMapper().readValue(exchange.getBody(), FacebookRefreshTokenResposne.class);

                // Update the expiration.
                Instant timestamp = new Date().toInstant();
                socialNetworkRegistration.setExpires(Date.from(timestamp.plusSeconds(facebookRefreshTokenResposne.getExpires_in())));
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
            facebook = new FacebookTemplate(socialNetworkRegistration.getToken());
            break;
        }

        return facebook;
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
            socialNetworkRegistration.setLastUsed(new Date());

            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            facebook = new FacebookTemplate(socialNetworkRegistration.getToken());

            posts.addAll(facebook.feedOperations().getFeed());
            posts.addAll(facebook.feedOperations().getPosts());
            posts.addAll(facebook.feedOperations().getTagged());
        }

        return posts;
    }

    /**
     * Nested class used soley to convert the refresh token response body from JSON to Java.
     *
     * Used by Jackson API to convert the JSON response to an instance of this type.
     */
    static private class FacebookRefreshTokenResposne implements Serializable {
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

        public FacebookRefreshTokenResposne() {
            this(null, null, 0L);
        }

        public FacebookRefreshTokenResposne(String access_token, String token_type, Long expires_in) {
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
