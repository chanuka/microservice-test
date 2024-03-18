package com.cba.core.auth.controller;

import com.cba.core.auth.dto.PermissionResponseDto;
import com.cba.core.auth.exception.JwtTokenException;
import com.cba.core.auth.service.PermissionService;
import com.cba.core.auth.service.TokenBlacklistService;
import com.cba.core.auth.util.JwtUtil;
import com.cba.core.auth.util.UserBeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final JwtUtil jwtUtil;
    private final UserBeanUtil userBeanUtil;
    private final JwtDecoder decoder;
    private final TokenBlacklistService tokenBlacklistService;
    private final MessageSource messageSource;
    private final PermissionService permissionService;


    @GetMapping("/test")
    public String test() {
        return "Success Test";
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam String token,
                                           @RequestParam String resource,
                                           @RequestParam String method) {

        Locale currentLocale = LocaleContextHolder.getLocale();// works only when as local statement

        Jwt claimsJws = jwtUtil.validateJwtToken(token, decoder);
        String username = claimsJws.getSubject();
        String validity = claimsJws.getClaimAsString("Validity");
        userBeanUtil.setUsername(username); // set user data in request scope for db updating
//        userBeanUtil.setRemoteAdr(request.getRemoteAddr()); // set remote address in request scope for db updating

        try {
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_TOKEN_BLACK_ERROR", null, currentLocale));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        boolean access = false;
        List<PermissionResponseDto> permissionResponseDtoList = null;
        try {
            permissionResponseDtoList = permissionService.findAllPermissionsByUser(username);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        access = permissionResponseDtoList.stream()
                .filter(permission -> permission.getResourceName().equals(resource)) //////
                .anyMatch(permission -> (
                        (method.equals("GET") && (permission.getReadd() != 0)) ||
                                (method.equals("POST") && (permission.getCreated() != 0)) ||
                                (method.equals("PUT") && (permission.getUpdated() != 0) ||
                                        (method.equals("DELETE") && (permission.getDeleted() != 0)))
                ));

        if (!access) {
            throw new JwtTokenException(token, messageSource.getMessage("GLOBAL_TOKEN_DENIED_ERROR", null, currentLocale));
//            return ResponseEntity.badRequest().body(messageSource.getMessage("GLOBAL_TOKEN_DENIED_ERROR", null, currentLocale));
        }
        return ResponseEntity.ok(token);
    }

}
