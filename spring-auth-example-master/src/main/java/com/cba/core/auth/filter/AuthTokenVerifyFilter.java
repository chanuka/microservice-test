package com.cba.core.auth.filter;

import com.cba.core.auth.config.JwtConfig;
import com.cba.core.auth.dto.PermissionResponseDto;
import com.cba.core.auth.exception.JwtTokenException;
import com.cba.core.auth.service.PermissionService;
import com.cba.core.auth.service.TokenBlacklistService;
import com.cba.core.auth.util.DeviceTypeEnum;
import com.cba.core.auth.util.JwtUtil;
import com.cba.core.auth.util.UserBeanUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthTokenVerifyFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;
    private final PermissionService permissionService;
    private final JwtDecoder decoder;
    private final UserBeanUtil userBeanUtil;
    private final TokenBlacklistService tokenBlacklistService;
    private final MessageSource messageSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Locale currentLocale = LocaleContextHolder.getLocale();// works only when as local statement
        logger.debug(messageSource.getMessage("GLOBAL_SECURITY_FILTER_DEBUG", null, currentLocale));

        String token = null;
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            logger.debug(messageSource.getMessage("GLOBAL_TOKEN_MISSING_ERROR", null, currentLocale));
            filterChain.doFilter(request, response);
            return;
        }

        try {

            token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "").trim();

            Jwt claimsJws = jwtUtil.validateJwtToken(token, decoder);
            String username = claimsJws.getSubject();
            String validity = claimsJws.getClaimAsString("Validity");
            userBeanUtil.setUsername(username); // set user data in request scope for db updating
            userBeanUtil.setRemoteAdr(request.getRemoteAddr()); // set remote address in request scope for db updating

            if (!validity.equals(String.valueOf(DeviceTypeEnum.WEB.getValue()))) {
                throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_TOKEN_MODULE_ERROR", null, currentLocale));
            }

            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_TOKEN_BLACK_ERROR", null, currentLocale));
            }

            String[] resourceArray = request.getRequestURI().split("\\/");
            boolean access = false;
            String method = request.getMethod();
            List<PermissionResponseDto> permissionResponseDtoList = permissionService.findAllPermissionsByUser(username);

            access = permissionResponseDtoList.stream()
                    .filter(permission -> permission.getResourceName().equals(resourceArray[1])) //////
                    .anyMatch(permission -> (
                            (method.equals("GET") && (permission.getReadd() != 0)) ||
                                    (method.equals("POST") && (permission.getCreated() != 0)) ||
                                    (method.equals("PUT") && (permission.getUpdated() != 0) ||
                                            (method.equals("DELETE") && (permission.getDeleted() != 0)))
                    ));

            access = true; /// / remove this
            if (!access) {
                logger.debug(messageSource.getMessage("GLOBAL_TOKEN_DENIED_ERROR", null, currentLocale));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_TOKEN_DENIED_ERROR", null, currentLocale));
            }


            var authorities = (List<Map<String, String>>) claimsJws.getClaim("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities =
                    authorities.stream()
                            .map(m -> new SimpleGrantedAuthority(m.get("role")))
                            .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtTokenException ex) {
            logger.error(ex.getMessage());
            throw ex;
        } catch (Exception se) {
            logger.error(se.getMessage());
            throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_INTERNAL_SERVER_ERROR", null, currentLocale));
        }
        filterChain.doFilter(request, response);
    }
}
