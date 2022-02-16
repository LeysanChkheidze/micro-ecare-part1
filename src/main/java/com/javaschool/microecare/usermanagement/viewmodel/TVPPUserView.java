package com.javaschool.microecare.usermanagement.viewmodel;

import com.javaschool.microecare.usermanagement.dao.TvppUser;

public class TVPPUserView {
    private int id;
    private String username;
    private String role;
    private boolean enabled;

    public TVPPUserView(TvppUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
