package com.hwann.common.filter;

import com.hwann.common.entity.User;
import com.hwann.common.repository.UserRepository;
import com.hwann.common.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String userEmail = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            userEmail = jwtTokenUtil.getEmailFromToken(jwtToken);
            System.out.println("이메일:" + userEmail);

            if (jwtTokenUtil.validateToken(jwtToken) && userEmail != null) {
                User user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

                GrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().name());

                Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getUserId(), null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handleInvalidToken(userEmail, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void handleInvalidToken(String userEmail, HttpServletResponse response) throws IOException {
        // 토큰이 만료되거나 유효하지 않은 경우
        String refreshToken = redisTemplate.opsForValue().get(userEmail);
        if (refreshToken != null && jwtTokenUtil.validateToken(refreshToken)) {
            String newAccessToken = jwtTokenUtil.generateAccessToken(userEmail);
            response.setHeader("Authorization", "Bearer " + newAccessToken);

            // 새로운 accessToken을 이용해 새로운 인증 객체 생성
            UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                    userRepository.findByEmail(userEmail).orElseThrow(
                                    () -> new UsernameNotFoundException("User not found during refresh: " + userEmail))
                            .getUserId(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token expired or invalid. Please log in again.");
        }
    }
}
