package edu.metrostate.ics499.prim.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This is a JPA Entity class that maps the SocialNetworkRegistration object to the Social Network Registration
 * table used for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name = "SOCIAL_NETWORK_REGISTRATION")
public class SocialNetworkRegistration implements Serializable {

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The date and time this registration was made.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The date and time this registration was made.
     */
    @Column(name = "created_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * The social network this registration is for.
     */
    @Column(name = "social_network", length = 128, nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialNetwork socialNetwork;

    /**
     * The token retrieved from the provisioning process and
     * used to retrieve information from the social network.
     */
    @Column(name = "token", length = 512, nullable = false)
    private String token;

    /**
     * The refresh token retrieved from the provisioning process and
     * used to renew the token for the social network. May be null
     * if the social network doesn't support refresh tokens.
     */
    @Column(name = "refresh_token", length = 512, nullable = true)
    private String refreshToken;

    /**
     * The time the token needs to be renewed by. If the token expires
     * it needs to be renewed or the provision process needs to be
     * completed again.
     */
    @Column(name = "expires", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;

    /**
     * The last time the token was used. Used to keep track of when the last
     * time data was retreived from the social network.
     */
    @Column(name = "last_used", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    /**
     * Gets id
     *
     * @return value of id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id to the specified value in id
     *
     * @param id the new value for id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name to the specified value in name
     *
     * @param name the new value for name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets createdTime
     *
     * @return value of createdTime
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets createdTime to the specified value in createdTime
     *
     * @param createdTime the new value for createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets socialNetwork
     *
     * @return value of socialNetwork
     */
    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    /**
     * Sets socialNetwork to the specified value in socialNetwork
     *
     * @param socialNetwork the new value for socialNetwork
     */
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    /**
     * Gets token
     *
     * @return value of token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token to the specified value in token
     *
     * @param token the new value for token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets refreshToken
     *
     * @return value of refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets refreshToken to the specified value in refreshToken
     *
     * @param refreshToken the new value for refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets expires
     *
     * @return value of expires
     */
    public Date getExpires() {
        return expires;
    }

    /**
     * Sets expires to the specified value in expires
     *
     * @param expires the new value for expires
     */
    public void setExpires(Date expires) {
        this.expires = expires;
    }

    /**
     * Gets lastUsed
     *
     * @return value of lastUsed
     */
    public Date getLastUsed() {
        return lastUsed;
    }

    /**
     * Sets lastUsed to the specified value in lastUsed
     *
     * @param lastUsed the new value for lastUsed
     */
    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialNetworkRegistration that = (SocialNetworkRegistration) o;
        return Objects.equals(getSocialNetwork(), that.getSocialNetwork());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSocialNetwork());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SocialNetworkRegistration{");
        sb.append("id=").append(id);
        sb.append(", socialNetwork='").append(socialNetwork).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", expires=").append(expires);
        sb.append(", lastUsed=").append(lastUsed);
        sb.append('}');
        return sb.toString();
    }
}
