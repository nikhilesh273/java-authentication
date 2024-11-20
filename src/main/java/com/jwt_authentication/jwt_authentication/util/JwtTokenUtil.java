package com.jwt_authentication.jwt_authentication.util;

import com.jwt_authentication.jwt_authentication.model.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.jwt_authentication.jwt_authentication.constant.JWTConstant.JWT_CLAIM_KEY;
import static com.jwt_authentication.jwt_authentication.constant.JWTConstant.JWT_SECRET;

/**
 * The {@code JwtTokenUtil} class provides utility methods for generating, parsing, and validating JWT tokens.
 *
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 * <p>
 * This class handles the JWT token operations such as extracting claims, generating tokens, and validating tokens.
 * It uses the {@code io.jsonwebtoken} library for JWT processing.
 * </p>
 */
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    /**
     * The validity duration of the JWT token, in seconds (5 hours).
     */
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // change the time.

    /**
     * Retrieves the username (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieves the user details stored in the JWT token's claims.
     *
     * @param token the JWT token
     * @return the {@link JwtUserDetails} extracted from the token
     */
    public static JwtUserDetails getClaimsFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return MapperUtil.objectToClass(claims.get(JWT_CLAIM_KEY), JwtUserDetails.class);
    }

    /**
     * Retrieves the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves a specific claim from the JWT token.
     *
     * @param <T>            the type of the claim
     * @param token          the JWT token
     * @param claimsResolver a function to extract a specific claim
     * @return the value of the claim
     */
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves all claims from the JWT token using the secret key.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return {@code true} if the token is expired; {@code false} otherwise
     */
    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param jwtUserDetails the user details to include in the token
     * @return the generated JWT token
     */
    public static String generateToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_CLAIM_KEY, jwtUserDetails);
        return doGenerateToken(claims, jwtUserDetails.getUsername(), jwtUserDetails);
    }

    /**
     * Generates a JWT token with the specified claims and subject.
     *
     * @param claims         the claims to include in the token
     * @param subject        the subject (typically username) for the token
     * @param jwtUserDetails the user details
     * @return the generated JWT token
     */
    @Autowired
    public static String doGenerateToken(Map<String, Object> claims, String subject, JwtUserDetails jwtUserDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    /**
     * Validates the given JWT token against the user details.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to validate against
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    public static Boolean validateToken(String token, JwtUserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}