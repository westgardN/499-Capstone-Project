package edu.metrostate.ics499.prim.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is a JPA Entity class that maps the User object to the User table used
 * for persistence. It is just a POJO with a default constructor.
 */
@Entity
@Table(name="PERSISTENT_LOGINS")
public class PersistentLogin implements Serializable {

    @Id
    private String series;

    @Column(name="username", unique=true, nullable=false)
    private String username;

    @Column(name="token", unique=true, nullable=false)
    private String token;

    @Column(name = "last_used", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersistentLogin that = (PersistentLogin) o;
        return Objects.equals(getSeries(), that.getSeries());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSeries());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PersistentLogin{");
        sb.append("series='").append(series).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", lastUsed=").append(lastUsed);
        sb.append('}');
        return sb.toString();
    }
}
