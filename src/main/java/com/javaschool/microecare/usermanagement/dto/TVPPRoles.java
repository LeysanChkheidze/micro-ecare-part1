package com.javaschool.microecare.usermanagement.dto;

public enum TVPPRoles {
    ROLE_ADMIN("ADMIN"),
    ROLE_EMPLOYEE("EMPLOYEE");

    String springRoleName;

    TVPPRoles(String springRole) {
        this.springRoleName = springRole;
    }

    public String getSpringRoleName() {
        return springRoleName;
    }

    public static String[] getAllRoles() {
        return new String[]{ROLE_ADMIN.springRoleName, ROLE_EMPLOYEE.springRoleName};
    }

}
