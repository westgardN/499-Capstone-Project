package edu.metrostate.ics499.prim.model;

import java.io.Serializable;

public class LinkedInCompany implements Serializable {
    private int id;
    private String name;

    /**
     * Gets id
     *
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id to the specified value in id
     *
     * @param id the new value for id
     */
    public void setId(int id) {
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
}
