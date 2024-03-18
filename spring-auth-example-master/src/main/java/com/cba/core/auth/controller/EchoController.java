///*
// * Copyright 2022 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.cba.core.auth.controller;
//
//import com.cba.core.auth.config.JwtConfig;
//import com.cba.core.auth.dto.JsonPayload;
//import com.cba.core.auth.dto.PermissionResponseDto;
//import com.cba.core.auth.service.PermissionService;
//import com.cba.core.auth.util.JwtUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.util.Base64;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Controller
//@RequiredArgsConstructor
//public class EchoController {
//
//    private static final Logger logger = LoggerFactory.getLogger(EchoController.class);
//
//    private final HttpServletRequest request;
//    private final JwtConfig jwtConfig;
//    private final JwtUtil jwtUtil;
//    private final JwtDecoder decoder;
//    private final PermissionService permissionService;
//
//
//    @RequestMapping(value = "/**", consumes = MediaType.ALL_VALUE, produces =
//            MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST,
//            RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
//    public ResponseEntity<JsonPayload> echoBack(@RequestBody(required = false) byte[] rawBody) {
//
//        final Map<String, String> headers = Collections.list(request.getHeaderNames()).stream()
//                .collect(Collectors.toMap(Function.identity(), request::getHeader));
//
//
//        final JsonPayload responsePayLoad = new JsonPayload();
//        responsePayLoad.set(JsonPayload.PROTOCOL, request.getProtocol());
//        responsePayLoad.set(JsonPayload.METHOD, request.getMethod());
//        responsePayLoad.set(JsonPayload.HEADERS, headers);
//        responsePayLoad.set(JsonPayload.COOKIES, request.getCookies());
//        responsePayLoad.set(JsonPayload.PARAMETERS, request.getParameterMap());
//        responsePayLoad.set(JsonPayload.PATH, request.getServletPath());
//        responsePayLoad.set(JsonPayload.BODY, rawBody != null ? Base64.getEncoder().encodeToString(rawBody) : null);
//        logger.info("REQUEST: {}", request.getParameterMap());
//
//        String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION.toLowerCase());
//        if (authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responsePayLoad);
//        } else {
//            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "").trim();
//            boolean access = false;
//
//            Jwt claimsJws = jwtUtil.validateJwtToken(token, decoder);
//            String username = claimsJws.getSubject();
//
//            System.out.println("request.getRequestURI(): " + headers.get("x-original-uri"));
//            if ("/auth/".equals(headers.get("x-original-uri"))) {
//                access = true;
//            } else {
//                if (headers.get("x-original-uri") != null) {
//                    String[] resourceArray = headers.get("x-original-uri").split("\\/");
//                    String method = request.getMethod();
//
//                    List<PermissionResponseDto> permissionResponseDtoList = null;
//                    try {
//                        permissionResponseDtoList = permissionService.findAllPermissionsByUser(username);
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//
//                    access = permissionResponseDtoList.stream()
//                            .filter(permission -> permission.getResourceName().equals(resourceArray[2]))
//                            .anyMatch(permission -> (
//                                    (method.equals("GET") && (permission.getReadd() != 0)) ||
//                                            (method.equals("POST") && (permission.getCreated() != 0)) ||
//                                            (method.equals("PUT") && (permission.getUpdated() != 0) ||
//                                                    (method.equals("DELETE") && (permission.getDeleted() != 0)))
//                            ));
//                }
//            }
//
//            if (!access) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsePayLoad);
//            }
//
//            return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
//        }
//
//    }
//
//}
