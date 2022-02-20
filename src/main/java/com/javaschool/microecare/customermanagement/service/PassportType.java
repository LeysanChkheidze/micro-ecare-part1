package com.javaschool.microecare.customermanagement.service;

public enum PassportType {
    NATIONAL_PASSPORT("national passport", 1),
    FOREIGN_PASSPORT("foreign passport", 2),
    DRIVERS_LICENCE("drivers licence", 3),
    PASSPORT_OF_CAT("passport of a cat", 4);

    private String displayName;
    private int typeID;

    PassportType(String displayName, int typeID) {
        this.displayName = displayName;
        this.typeID = typeID;
    }

    public static PassportType getPassportType(int id) {
        for (PassportType type : values()) {
            if (type.typeID == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid type id: " + id);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
}
