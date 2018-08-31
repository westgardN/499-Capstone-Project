package edu.metrostate.ics499.prim.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * The SecurityToken class is a JPA Entity for tokens that
 * are associated with a user and used for authentication
 * and authorization purposes.
 */
@Entity
@Table(name = "SECURITY_TOKEN")
public class SecurityToken {
    /**
     * The default number of minutes that the token is alive.
     */
    private static final long DEFAULT_EXPIRATION = 60 * 24; // One day!

    /**
     * The auto generated Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user associated with this security token
     */
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The actual token
     */
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * The date the token was created
     */
    @Column(name = "created_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    /**
     * The date the token expires
     */
    @Column(name = "expiration_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    /**
     * Calculates the expiration date based on the current date and time plus
     * the specified number of minutes.
     *
     * @param minutesFromNow the number of minutes from now to expire the token.
     *
     * @return the calculated expiration date.
     */
    private Date calculateExpiration(long minutesFromNow) {
        return Date.from(LocalDateTime.now().plusMinutes(minutesFromNow).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Creates a new token. The associated user, token string, and expiration date will
     * need to be set after creation.
     */
    public SecurityToken() {
        this(null, null, DEFAULT_EXPIRATION);
    }

    /**
     * Creates a new SecurityToken from the specified token string. Needs to be associated with a user
     * before it can be saved.
     *
     * @param token the token string for this SecurityToken
     */
    public SecurityToken(String token) {
        this(null, token, DEFAULT_EXPIRATION);
    }

    /**
     * Creates a new token associated with the specified user and token. The token expires after
     * DEFAULT_EXPIRATION minutes from now.
     *
     * @param user the User this token is form.
     * @param token the token string.
     */
    public SecurityToken(User user, String token) {
        this(user, token, DEFAULT_EXPIRATION);
    }

    /**
     * Creates a new token associated with the specified user and token. The token expires after
     * the specified number of minutes from now if minutes is &gt; 0.
     *
     * @param user the User this token is form.
     * @param token the token string.
     * @param minutes the number of minutes from now to expire this token from. If minutes is &lt;= 0
     *                then the created token will not have an expiration date.
     */
    public SecurityToken(User user, String token, long minutes) {
        this.user = user;
        this.token = token;
        this.createdDate = new Date();
        this.expirationDate = minutes > 0 ? calculateExpiration(minutes) : null;
    }

    public void update(final String token) {
        update(token, DEFAULT_EXPIRATION);
    }

    public void update(final String token, long minutes) {
        this.token = token;
        this.expirationDate = calculateExpiration(minutes);
    }

    /**
     * Returns true if this token has expired. If expiration date is not set or null, then false
     * is always returned.
     *
     * @return true if this token has expired.
     */
    public boolean isExpired() {
        boolean result = false;

        if (expirationDate != null) {
            result = expirationDate.before(new Date());
        }

        return result;
    }

    /**
     * Gets DEFAULT_EXPIRATION
     *
     * @return value of DEFAULT_EXPIRATION
     */
    public static long getDefaultExpiration() {
        return DEFAULT_EXPIRATION;
    }

    /**
     * Gets id
     *
     * @return value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id to the specified value in id
     *
     * @param id the new value for id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets user
     *
     * @return value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user to the specified value in user
     *
     * @param user the new value for user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets createdDate
     *
     * @return value of createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets createdDate to the specified value in createdDate
     *
     * @param createdDate the new value for createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets expirationDate
     *
     * @return value of expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets expirationDate to the specified value in expirationDate
     *
     * @param expirationDate the new value for expirationDate
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityToken that = (SecurityToken) o;
        return Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SecurityToken{");
        sb.append("id=").append(id);
        sb.append(", token='").append(token).append('\'');
        sb.append(", user=").append(user);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", expirationDate=").append(expirationDate);
        sb.append('}');
        return sb.toString();
    }
}
