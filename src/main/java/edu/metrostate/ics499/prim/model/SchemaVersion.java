package edu.metrostate.ics499.prim.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

/**
 * This is a JPA Entity class that maps the Role object to the Role table used
 * for persistence. It is just a POJO with a default constructor. New instances
 * default to the USER role.
 */
@Entity
@Table(name = "SCHEMA_VERSION")
public class SchemaVersion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "major", nullable = false)
    private Integer major;

    @Column(name = "minor", nullable = false)
    private Integer minor;

    @Column(name = "revision", nullable = false)
    private Integer revision;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SchemaVersion{");
        sb.append("id=").append(id);
        sb.append(", major=").append(major);
        sb.append(", minor=").append(minor);
        sb.append(", revision=").append(revision);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemaVersion that = (SchemaVersion) o;
        return Objects.equals(getMajor(), that.getMajor()) &&
                Objects.equals(getMinor(), that.getMinor()) &&
                Objects.equals(getRevision(), that.getRevision());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getMajor(), getMinor(), getRevision());
    }
}
