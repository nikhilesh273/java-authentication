package com.jwt_authentication.jwt_authentication.filter;

import com.jwt_authentication.jwt_authentication.model.JwtUserDetails;
import com.jwt_authentication.jwt_authentication.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nikhilesh babu mt
 * @created 29-Mar-2022
 */

/**
 * The {@code JwtRequestFilter} class is a custom filter that intercepts each HTTP request to extract and validate a JWT token.
 * <p>
 * This class extends {@link OncePerRequestFilter} to ensure that the filter is executed only once per request.
 * It performs the following operations:
 * <ul>
 *     <li>Checks for a JWT token in the "Authorization" header or "token" query parameter.</li>
 *     <li>Extracts the user details from the token.</li>
 *     <li>Validates the token and sets the authentication in the {@link org.springframework.security.core.context.SecurityContextHolder}.</li>
 * </ul>
 * </p>
 */
public class JwtRequestFilter extends OncePerRequestFilter {


    /**
     * This method filters each incoming HTTP request to extract and validate a JWT token.
     * <p>
     * If the JWT token is valid, the user details are extracted, and the authentication is set in the security context.
     * If no valid token is found, the request proceeds as is.
     * </p>
     *
     * @param request     the {@link HttpServletRequest} object representing the incoming request
     * @param response    the {@link HttpServletResponse} object representing the outgoing response
     * @param filterChain the {@link FilterChain} to pass the request to the next filter
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String autherization = request.getHeader("Authorization");
        String queryToken = request.getParameter("token");

        String jwtTockeString = null;
        JwtUserDetails userDetails = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (autherization != null
                && autherization.startsWith("Bearer ")) {

            jwtTockeString = autherization.substring(7);
            userDetails = JwtTokenUtil.getClaimsFromToken(jwtTockeString);
        } else if (queryToken != null) {

            jwtTockeString = queryToken;
            userDetails = JwtTokenUtil.getClaimsFromToken(jwtTockeString);

        }

        if (userDetails != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (JwtTokenUtil
                    .validateToken(jwtTockeString, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }

}
