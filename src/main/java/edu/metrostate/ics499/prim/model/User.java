package edu.metrostate.ics499.prim.model;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

/**
 * This is a JPA Entity class that maps the User object to the User table used
 * for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The unique username for this user. It is used for storing cookies but not authentication.
     */
    @Column(name = "username", length = 64, unique = true, nullable = false)
    private String username;

    /**
     * The password for this user.
     */
    @Column(name = "password", length = 100, nullable = false)
    private String password;

    /**
     * The user's first name. Must be provided.
     */
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    /**
     * The user's last name. Must be provided.
     */
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    /**
     * The unique e-mail address for this user. Must be provided.
     */
    @Column(name = "email", length = 64, unique = true, nullable = false)
    private String email;

    /**
     * The unique Single Sign-on user id for this user. This is used for authorization and must be provided.
     */
    @Column(name = "sso_id", length = 64, unique = true, nullable = false)
    private String ssoId;

    /**
     * The status of this user. Only active users can login.
     */
    @Column(name = "status", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.NOT_ACTIVATED;

    /**
     * Whether the account is enabled or disabled. Disabled accounts cannot login
     * unless an admin re-enables them.
     */
    @Column(name = "enabled", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean enabled = false;

    /**
     * The number of consecutive failed logins since the last successful login.
     */
    @Column(name = "failed_logins", nullable = false)
    private Integer failedLogins = 0;

    /**
     * The date and time the user last attempted to login.
     */
    @Column(name = "last_visited_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitedOn;

    /**
     * The IPv6 Address that was last captured when the user last tried to login.
     */
    @Column(name = "last_visited_from", length = 100, nullable = true)
    private String lastVisitedFrom;

    /**
     * The date and time the user last changed their password.
     */
    @Column(name = "last_password_changed_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChangedOn;

    /**
     * The date and time the user account was activated.
     */
    @Column(name = "activated_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedOn;

    /**
     * The roles the user has assigned to them.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<Role>();

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
     * Gets username
     *
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username to the specified value in username
     *
     * @param username the new value for username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password
     *
     * @return value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password to the specified value in password
     *
     * @param password the new value for password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets firstName
     *
     * @return value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets firstName to the specified value in firstName
     *
     * @param firstName the new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets lastName
     *
     * @return value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets lastName to the specified value in lastName
     *
     * @param lastName the new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email
     *
     * @return value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email to the specified value in email
     *
     * @param email the new value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets ssoId
     *
     * @return value of ssoId
     */
    public String getSsoId() {
        return ssoId;
    }

    /**
     * Sets ssoId to the specified value in ssoId
     *
     * @param ssoId the new value for ssoId
     */
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    /**
     * Gets status
     *
     * @return value of status
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Sets status to the specified value in status
     *
     * @param status the new value for status
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Gets enabled
     *
     * @return value of enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets enabled to the specified value in enabled
     *
     * @param enabled the new value for enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets failedLogins
     *
     * @return value of failedLogins
     */
    public Integer getFailedLogins() {
        return failedLogins;
    }

    /**
     * Sets failedLogins to the specified value in failedLogins
     *
     * @param failedLogins the new value for failedLogins
     */
    public void setFailedLogins(Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    /**
     * Gets lastVisitedOn
     *
     * @return value of lastVisitedOn
     */
    public Date getLastVisitedOn() {
        return lastVisitedOn;
    }

    /**
     * Sets lastVisitedOn to the specified value in lastVisitedOn
     *
     * @param lastVisitedOn the new value for lastVisitedOn
     */
    public void setLastVisitedOn(Date lastVisitedOn) {
        this.lastVisitedOn = lastVisitedOn;
    }

    /**
     * Gets lastVisitedFrom
     *
     * @return value of lastVisitedFrom
     */
    public String getLastVisitedFrom() {
        return lastVisitedFrom;
    }

    /**
     * Sets lastVisitedFrom to the specified value in lastVisitedFrom
     *
     * @param lastVisitedFrom the new value for lastVisitedFrom
     */
    public void setLastVisitedFrom(String lastVisitedFrom) {
        this.lastVisitedFrom = lastVisitedFrom;
    }

    /**
     * Gets lastPasswordChangedOn
     *
     * @return value of lastPasswordChangedOn
     */
    public Date getLastPasswordChangedOn() {
        return lastPasswordChangedOn;
    }

    /**
     * Sets lastPasswordChangedOn to the specified value in lastPasswordChangedOn
     *
     * @param lastPasswordChangedOn the new value for lastPasswordChangedOn
     */
    public void setLastPasswordChangedOn(Date lastPasswordChangedOn) {
        this.lastPasswordChangedOn = lastPasswordChangedOn;
    }

    /**
     * Gets activatedOn
     *
     * @return value of activatedOn
     */
    public Date getActivatedOn() {
        return activatedOn;
    }

    /**
     * Sets activatedOn to the specified value in activatedOn
     *
     * @param activatedOn the new value for activatedOn
     */
    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    /**
     * Gets roles
     *
     * @return value of roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles to the specified value in roles
     *
     * @param roles the new value for roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getSsoId(), user.getSsoId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUsername(), getSsoId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", ssoId='").append(ssoId).append('\'');
        sb.append(", status=").append(status);
        sb.append(", failedLogins=").append(failedLogins);
        sb.append(", lastVisitedOn=").append(lastVisitedOn);
        sb.append(", lastVisitedFrom='").append(lastVisitedFrom).append('\'');
        sb.append(", activatedOn=").append(activatedOn);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}