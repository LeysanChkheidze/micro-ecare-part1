package com.javaschool.microecare.usermanagement.dto;

public enum TVPPRoles {
    ROLE_ADMIN("ADMIN"),
    ROLE_EMPLOYEE("EMPLOYEE"),
    ROLE_VP2("VP2");

    String springRoleName;

    TVPPRoles(String springRole) {
        this.springRoleName = springRole;
    }

    public String getSpringRoleName() {
        return springRoleName;
    }

    public static String[] getAllRoles() {
        return new String[]{ROLE_ADMIN.springRoleName, ROLE_EMPLOYEE.springRoleName, ROLE_VP2.springRoleName};
    }

    public static TVPPRoles getRoleByName(String name) {
        return valueOf(name);

    }

}
