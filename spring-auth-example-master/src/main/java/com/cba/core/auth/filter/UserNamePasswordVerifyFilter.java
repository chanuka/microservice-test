package com.cba.core.auth.filter;


import com.cba.core.auth.config.JwtConfig;
import com.cba.core.auth.dto.UsernameAndPasswordAuthenticationRequestDto;
import com.cba.core.auth.model.TokenRefresh;
import com.cba.core.auth.service.RefreshTokenService;
import com.cba.core.auth.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class UserNamePasswordVerifyFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final JwtEncoder encoder;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            logger.debug("CustomUserNamePasswordFilter called--");

            UsernameAndPasswordAuthenticationRequestDto authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequestDto.class);

//            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword());
//            Authentication authenticate = authenticationManager.authenticate(authentication);

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            authRequest.setDetails(authenticationRequest); // set user object for future usage - optional
            Authentication authenticate = authenticationManager.authenticate(authRequest);
            return authenticate;

        } catch (BadCredentialsException e) {
            request.setAttribute("errorObject", e);
            request.setAttribute("errorCode", "BadCredentialsException");
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = jwtUtil.generateTokenFromAuthResult(authResult, encoder);
        TokenRefresh refreshToken = null;
        try {
            refreshToken = refreshTokenService.createRefreshToken(authResult.getName());
        } catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
        response.addHeader("Refresh_Token", "" + refreshToken.getToken());
    }

}
