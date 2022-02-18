package com.javaschool.microecare.usermanagement.viewmodel;

import com.javaschool.microecare.usermanagement.dao.TvppUser;

public class TVPPUserView implements Comparable<TVPPUserView>{
    private long id;
    private String username;
    private String role;
    private boolean enabled;

    public TVPPUserView(TvppUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.enabled = user.isEnabled();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public int compareTo(TVPPUserView o) {
        return this.username.compareTo(o.getUsername());
    }
}
