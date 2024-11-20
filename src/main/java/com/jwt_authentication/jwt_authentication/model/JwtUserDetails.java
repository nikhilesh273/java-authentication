package com.jwt_authentication.jwt_authentication.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 */
@Data
public class JwtUserDetails implements UserDetails {

    private String firstName;
    private String username;
    private String lastName;
    private String mobileNo;
    private String email;
    private Long roleId;
    Integer timeZoneDifference;
    private String orgCode;
    private Collection<JWTGrantedAuthority> authorities;

    public JwtUserDetails() {
    }

    public JwtUserDetails(String username, String firstName, String lastName, String mobileNo,
                          String email, Long roleId, Integer timeZoneDifference, String orgCode,
                          Collection<JWTGrantedAuthority> authorities) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mobileNo = mobileNo;
        this.email = email;
        this.roleId = roleId;
        this.timeZoneDifference = timeZoneDifference;
        this.orgCode = orgCode;
        this.authorities = authorities;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<JWTGrantedAuthority> getAuthorities() {
        return authorities;
    }

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

    @Override
    public boolean isEnabled() {
        return true;
    }

}