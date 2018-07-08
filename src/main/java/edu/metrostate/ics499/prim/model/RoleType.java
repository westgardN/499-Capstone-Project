package edu.metrostate.ics499.prim.model;

import java.io.Serializable;

/**
 * An enum that can be used to test the role of a user.
 * If more roles are added, this enum will need to be updated.
 */
public enum RoleType implements Serializable{
    /**
     * A team member.
     */
    USER("USER"),
    /**
     * A database administrator.
     */
    DBA("DBA"),
    /**
     * An administrator that has complete control of PRIM.
     */
    ADMIN("ADMIN");

    String roleType;

    private RoleType(String roleType){
        this.roleType = roleType;
    }

    /**
     * Returns the RoleType as a String.
     *
     * @return the RoleType as a String.
     */
    public String getRoleType(){
        return roleType;
    }

}