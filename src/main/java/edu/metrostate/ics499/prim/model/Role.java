package edu.metrostate.ics499.prim.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * This is a JPA Entity class that maps the Role object to the Role table used
 * for persistence. It is just a POJO with a default constructor. New instances
 * default to the USER role.
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", length = 64, unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType type = RoleType.USER;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getType(), role.getType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getType());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Role{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
