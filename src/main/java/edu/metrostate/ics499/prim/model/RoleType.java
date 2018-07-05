package edu.metrostate.ics499.prim.model;

import java.io.Serializable;

/**
 * An enum that can be used to test the role of a user.
 * If more roles are added, this enum will need to be updated.
 */
public enum RoleType implements Serializable{
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");

    String roleType;

    private RoleType(String roleType){
        this.roleType = roleType;
    }

    public String getRoleType(){
        return roleType;
    }

}