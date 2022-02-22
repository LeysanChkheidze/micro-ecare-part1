package com.javaschool.microecare.customermanagement.service;

public enum LockInitiator {
    VP1("Telekom"), VP2("Customer");

    String displayName;

    LockInitiator(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
