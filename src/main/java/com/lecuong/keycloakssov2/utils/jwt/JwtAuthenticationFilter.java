package com.lecuong.keycloakssov2.utils.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecuong.keycloakssov2.exception.BusinessCodeResponse;
import com.lecuong.keycloakssov2.exception.BusinessException;
import com.lecuong.keycloakssov2.modal.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author CuongLM
 * @created 25/05/2024 - 21:05
 * @project keycloak-sso-v2
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final List<String> apiBase = List.of("/api/v1/users/test");
        Predicate<HttpServletRequest> isApiBase =
                r -> apiBase
                        .stream()
                        .anyMatch(uri -> r.getRequestURI().toLowerCase().contains(uri.toLowerCase()));

        if (isApiBase.test(request)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validate(jwt)) {
                filterChain.doFilter(request, response);
            } else {
                HttpServletResponse resp = response;
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                BusinessException error = new BusinessException(BusinessCodeResponse.TOKEN);
                this.objectMapper.writeValue(resp.getOutputStream(), BaseResponse.ofFail(error));
            }
            return;
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
