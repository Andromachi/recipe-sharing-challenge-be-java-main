package net.azeti.challenge.recipe.authentication;

import lombok.RequiredArgsConstructor;
import net.azeti.challenge.recipe.authentication.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * /**
     * The doFilterInternal method is called for every request made to the application.
     * It extracts the JWT from the Authorization header and validates it.
     * If the token is valid, it creates a {@link UsernamePasswordAuthenticationToken} for the authenticated user.
     * The token includes the user's details and their authorities (roles), but not their password, because the user is
     * assumed to already be authenticated since they have a valid JWT. This token is then used to set the authentication
     * into the {@link SecurityContextHolder}, effectively letting the system know that the current user is authenticated.
     *
     * @param request     the servlet request.
     * @param response    the servlet response.
     * @param filterChain a chain of filters that should be applied.
     * @throws ServletException in case of a servlet error.
     * @throws IOException      in case of an I/O error.
     */

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
