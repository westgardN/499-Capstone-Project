package edu.metrostate.ics499.prim.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @NotEmpty
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "sso_id", unique = true, nullable = false)
    private String ssoId;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "failed_logins", nullable = false)
    private int failedLogins;

    @Column(name = "last_visited_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitedOn;

    @Column(name = "last_visited_from", nullable = true)
    private String lastVisitedFrom;

    @Column(name = "user_key", nullable = true)
    private String userKey;

    @Column(name = "activated_on", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedOn;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<Role>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public Date getLastVisitedOn() {
        return lastVisitedOn;
    }

    public void setLastVisitedOn(Date lastVisitedOn) {
        this.lastVisitedOn = lastVisitedOn;
    }

    public String getLastVisitedFrom() {
        return lastVisitedFrom;
    }

    public void setLastVisitedFrom(String lastVisitedFrom) {
        this.lastVisitedFrom = lastVisitedFrom;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Set<Role> getRoles() {
        return roles;
    }

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
        sb.append(", userKey='").append(userKey).append('\'');
        sb.append(", activatedOn=").append(activatedOn);
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}