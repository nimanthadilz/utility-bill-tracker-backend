package com.nimantha.utilitybilltracker.config;

import com.nimantha.utilitybilltracker.dto.UserDTO;
import com.nimantha.utilitybilltracker.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String usernameInToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            // Token validation will happen in this line
            usernameInToken = jwtService.extractUsername(jwtToken);

            if (usernameInToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDTO userDTO = new UserDTO(usernameInToken);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDTO,
                                                                                                        null,
                                                                                                        null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception exception) {
            // something went wrong when handling the jwt token
            System.out.println(exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
