package com.javaschool.microecare.usermanagement.auth;

import com.javaschool.microecare.customermanagement.dao.Customer;
import com.javaschool.microecare.usermanagement.dao.TvppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;
    private final boolean isEnabled;

    public UserDetailsImpl(TvppUser user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
        this.isEnabled = user.isEnabled();
    }

    public UserDetailsImpl(Customer customer) {
        this.username = customer.getLoginData().getEmail();
        this.password = customer.getLoginData().getPassword();
        this.rolesAndAuthorities = List.of(new SimpleGrantedAuthority(customer.getLoginData().getRole()));
        this.isEnabled = customer.getLoginData().isEnabled();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    //remaining methods that just return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
