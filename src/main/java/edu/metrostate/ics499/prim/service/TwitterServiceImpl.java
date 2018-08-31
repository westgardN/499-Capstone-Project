package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;

/**
 * The TwitterServiceImpl is a Spring Social based implementation of the TwitterService interface.
 */
@Service("twitterService")
public class TwitterServiceImpl implements TwitterService {
    @Value("${spring.social.twitter.authUri}")
    String twitterAuthUri;

    @Value("${spring.social.twitter.key}")
    String twitterKey;

    @Value("${spring.social.twitter.secret}")
    String twitterSecret;

    @Value("${spring.social.twitter.consumer.key}")
    String twitterConsumerKey;

    @Value("${spring.social.twitter.consumer.secret}")
    String twitterConsumerSecret;

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
        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterConsumerKey, twitterConsumerSecret);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken requestToken = oauthOperations.fetchRequestToken(twitterAuthUri, null);
        String authorizeUrl = oauthOperations.buildAuthorizeUrl(requestToken.getValue(), OAuth1Parameters.NONE);
        return authorizeUrl;
    }

    /**
     * Registers Twitter using the specified token and verification code.
     *
     * @param oauthToken    the token received from the client request.
     * @param oauthVerifier the verification code from the client request.
     */
    @Override
    @Transactional
    public void registerTwitter(String oauthToken, String oauthVerifier) {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService.findNonExpiredBySocialNetwork(SocialNetwork.TWITTER);

        TwitterConnectionFactory connectionFactory = new TwitterConnectionFactory(twitterConsumerKey, twitterConsumerSecret);
        OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuthToken token = new OAuthToken(oauthToken, oauthVerifier);
        OAuthToken accessGrant = oauthOperations.exchangeForAccessToken(new AuthorizedRequestToken(token, oauthVerifier), null);
        Twitter twitter = getTwitter(accessGrant.getValue(), accessGrant.getSecret());
        String name = twitter.userOperations().getScreenName();
        long id = twitter.userOperations().getProfileId();

        if (socialNetworkRegistrationList.isEmpty()) {
            socialNetworkRegistrationService.register(SocialNetwork.TWITTER, accessGrant, name);
        } else {
            Date now = new Date();
            boolean found = false;

            for (int i = 0; i < socialNetworkRegistrationList.size(); i++) {
                SocialNetworkRegistration socialNetworkRegistration = socialNetworkRegistrationList.get(i);

                Twitter current = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());

                long idCurrent = current.userOperations().getProfileId();

                if (Objects.equals(idCurrent, id) == true) {
                    found = true;
                    socialNetworkRegistration.setToken(accessGrant.getValue());
                    socialNetworkRegistration.setRefreshToken(accessGrant.getSecret());
                    // Expire 1 year from now.
                    socialNetworkRegistration.setExpires(Date.from(now.toInstant().plus(Duration.ofDays(365))));
                    socialNetworkRegistration.setLastUsed(now);
                    break;
                }
            }

            if (!found) {
                socialNetworkRegistrationService.register(SocialNetwork.TWITTER, accessGrant, name);
            }
        }
    }

    /**
     * Refreshes the specified SocialNetworkRegistration token. Does nothing for Twitter except extend
     * the expiration one year from now.
     *
     * @param socialNetworkRegistration the registration to refresh authorization token to for.
     */
    @Override
    public void refreshToken(SocialNetworkRegistration socialNetworkRegistration) {
        // Expire 1 year from now.
        socialNetworkRegistration.setExpires(Date.from((new Date()).toInstant().plus(Duration.ofDays(365))));
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

    private Twitter getTwitter(String key, String secret) {
        return new TwitterTemplate(twitterConsumerKey, twitterConsumerSecret, key, secret);
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
            twitter = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());
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

        List<Twitter> twitters = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
                socialNetworkRegistrationService.update(socialNetworkRegistration);
            }

            if (!socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                twitters.add(getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken()));
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
        List<Tweet> tweets = new LinkedList<>();

        tweets.addAll(getAllTweetItems(twitter));
        tweets.addAll(getAllRetweetItems(twitter));
        tweets.addAll(getAllMentionItems(twitter));

        return tweets;
    }

    @Override
    public List<Tweet> getAllTweetItems(Twitter twitter) {
        List<Tweet> tweets = new LinkedList<>();

        for (Tweet tweet : twitter.timelineOperations().getHomeTimeline()) {
            tweets.add(tweet);
        }

        return tweets;
    }

    @Override
    public List<Tweet> getAllRetweetItems(Twitter twitter) {
        List<Tweet> tweets = new LinkedList<>();

        for (Tweet tweet : twitter.timelineOperations().getRetweetsOfMe()) {
            tweets.add(tweet);
        }

        return tweets;
    }

    @Override
    public List<Tweet> getAllMentionItems(Twitter twitter) {
        List<Tweet> tweets = new LinkedList<>();

        for (Tweet tweet : twitter.timelineOperations().getMentions()) {
            tweets.add(tweet);
        }

        return tweets;
    }

    /**
     * Returns a list of all supported tweets from the authenticated accounts.
     *
     * @return a list of all supported tweets from the authenticated accounts.
     */
    @Transactional
    public List<Tweet> getAllTweetItems() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;
        List<Tweet> tweets = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            twitter = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());

            tweets.addAll(getAllTweetItems(twitter));
            socialNetworkRegistration.setLastUsed(new Date());
        }

        return tweets;
    }

    /**
     * Returns a list of all supported retweets from the authenticated accounts.
     *
     * @return a list of all supported retweets from the authenticated accounts.
     */
    @Transactional
    public List<Tweet> getAllRetweetItems() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;
        List<Tweet> tweets = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            twitter = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());

            tweets.addAll(getAllRetweetItems(twitter));
            socialNetworkRegistration.setLastUsed(new Date());
        }

        return tweets;
    }

    /**
     * Returns a list of all supported mentions from the authenticated accounts.
     *
     * @return a list of all supported mentions from the authenticated accounts.
     */
    @Transactional
    public List<Tweet> getAllMentionItems() {
        List<SocialNetworkRegistration> socialNetworkRegistrationList = socialNetworkRegistrationService
                .findBySocialNetwork(SocialNetwork.TWITTER);

        Twitter twitter = null;
        List<Tweet> tweets = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            twitter = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());

            tweets.addAll(getAllMentionItems(twitter));
            socialNetworkRegistration.setLastUsed(new Date());
        }

        return tweets;
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
        List<Tweet> tweets = new LinkedList<>();

        for (SocialNetworkRegistration socialNetworkRegistration : socialNetworkRegistrationList) {
            if (socialNetworkRegistrationService.isExpired(socialNetworkRegistration)) {
                refreshToken(socialNetworkRegistration);
            }

            twitter = getTwitter(socialNetworkRegistration.getToken(), socialNetworkRegistration.getRefreshToken());

            tweets.addAll(getAllTweetTypeItems(twitter));
            socialNetworkRegistration.setLastUsed(new Date());
        }

        return tweets;
    }

    private Interaction interactionFromTweet(Tweet tweet, InteractionType interactionType) {
        Interaction interaction = new Interaction();

        Date createdTime = tweet.getCreatedAt();
        interaction.setCreatedTime(createdTime != null ? createdTime : new Date());

        interaction.setFlag(InteractionFlag.NEW);

        interaction.setFromId("" + tweet.getFromUserId());
        interaction.setFromName(tweet.getFromUser());

        interaction.setMessageId(tweet.getIdStr());
        interaction.setMessage(tweet.getText());

        String linkComment = "https://twitter.com/intent/tweet?in-reply-to=" + tweet.getIdStr();

//        if (tweet.getEntities().hasUrls()) {
//            for (UrlEntity url : tweet.getEntities().getUrls()) {
//                linkComment = url.getDisplayUrl();
//                final Map<String, Object> extraData = url.getExtraData();
//            }
//        }
        interaction.setMessageLink(linkComment);

        interaction.setSocialNetwork(SocialNetwork.TWITTER);
        interaction.setState(InteractionState.OPEN);
        interaction.setType(interactionType);

        return interaction;
    }

    /**
     * Returns a List of Interactions or an empty list if there is no data.
     *
     * @return a List of Interactions or an empty list if there is no data.
     */
    @Override
    public List<Interaction> getInteractions() {
        List<Interaction> interactions = new ArrayList<>();
        Set<Interaction> hashInteractions = new HashSet<>();

        // Get the Twitter Feed data.
        for (Tweet tweet : getAllTweetItems()) {
            hashInteractions.add(interactionFromTweet(tweet, InteractionType.TWEET));
        }

        for (Tweet tweet : getAllRetweetItems()) {
            hashInteractions.add(interactionFromTweet(tweet, InteractionType.RETWEET));
        }

        for (Tweet tweet : getAllMentionItems()) {
            hashInteractions.add(interactionFromTweet(tweet, InteractionType.MENTION));
        }

        interactions.addAll(hashInteractions);

        return interactions;
    }

}
