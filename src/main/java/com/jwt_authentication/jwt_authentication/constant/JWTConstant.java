package com.jwt_authentication.jwt_authentication.constant;

/**
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 */

/**
 * The {@code JWTConstant} class provides constant values used for handling JWTs in the application.
 * -----------------------------------------------------------------------------------------------
 * <p>
 * This class is designed as a utility class with a private constructor to prevent instantiation.
 * It contains static fields that hold configuration values for JWT processing.
 * </p>
 */
public class JWTConstant {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JWTConstant() {
    }

    /**
     * The secret key used for signing and verifying JWT tokens.
     * ---------------------------------------------------------
     * <p>
     * This value is retrieved from the environment variable {@code JWT_SECRET}.
     * </p>
     */
    public static final String JWT_SECRET = System.getenv("JWT_SECRET");

    /**
     * The claim key used to store user-related information in the JWT payload.
     * ---------------------------------------------------------------------
     * <p>
     * This is used to identify and extract the user data from the JWT.
     * </p>
     */
    public static final String JWT_CLAIM_KEY = "user";
}
