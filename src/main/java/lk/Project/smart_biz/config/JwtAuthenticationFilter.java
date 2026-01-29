package lk.Project.smart_biz.config;

import io.jsonwebtoken.Claims;
import lk.Project.smart_biz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7).trim();

        try {
            String username = jwtUtil.extractUsername(token);

            Claims claims = jwtUtil.extractAllClaims(token);
            String role = claims.get("role", String.class);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + role));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
        }
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) ||
                path.equals("/api/v1/admin/login") ||
                path.equals("/api/v1/admin/save") ||
                path.equals("/api/v1/business/login") ||
                path.equals("/api/v1/owner/save");
    }
}
