package com.jwt_authentication.jwt_authentication.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 */

/**
 * The {@code JWTGrantedAuthority} class represents a custom implementation of the {@link GrantedAuthority} interface.
 * <p>
 * This class is used to encapsulate the authority/role information for a user, which is typically derived from a JWT.
 * </p>
 */
public class JWTGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 560L;
    private String authority;

    /**
     * Default constructor.
     * <p>
     * Creates an empty instance of {@code JWTGrantedAuthority}.
     * This is required for certain serialization and deserialization scenarios.
     * </p>
     */
    public JWTGrantedAuthority() {
    }

    /**
     * Parameterized constructor.
     * <p>
     * Initializes the {@code JWTGrantedAuthority} with a specified role or authority.
     * </p>
     *
     * @param role the role or authority associated with this instance
     */
    public JWTGrantedAuthority(String role) {
        this.authority = role;
    }

    /**
     * Retrieves the authority or role associated with this instance.
     * <p>
     * This method is a requirement of the {@link GrantedAuthority} interface.
     * </p>
     *
     * @return the authority or role as a {@link String}
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }

    /**
     * Sets the authority or role for this instance.
     *
     * @param role the role or authority to be set
     */
    public void setAuthority(String role) {
        this.authority = role;
    }
}