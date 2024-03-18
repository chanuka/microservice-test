package com.cba.core.gateway.filter;

import com.cba.core.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private  RouteValidator validator;
    @Autowired
    private  WebClient.Builder webClientBuilder; //support for load balanced and eureka service discovery by default
//    @Autowired
//    private  ReactorLoadBalancerExchangeFilterFunction lbFunction;
    @Autowired
    private  JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }


                System.out.println("URI:" + exchange.getRequest().getURI() + " Path:" + exchange.getRequest().getPath());

                String[] resourceArray = exchange.getRequest().getPath().toString().split("\\/");

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);

//                    //REST call to AUTH service
//                        template.getForObject("http://localhost:8088//auth//validate?" +
//                                        "token=" + authHeader +
//                                        "&resource=" + resourceArray[2] +
//                                        "&method=" + exchange.getRequest().getMethod().toString(),
//                                String.class);
//                    jwtUtil.validateToken(authHeader);


                        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                                .scheme("http")
                                .host("auth-service")
                                .path("/auth/validate")
                                .queryParam("token", authHeader)
                                .queryParam("resource", resourceArray[2])
                                .queryParam("method", exchange.getRequest().getMethod().toString());

                        WebClient webClient = webClientBuilder.baseUrl(builder.toUriString()).build();
//                        WebClient webClient = webClientBuilder.baseUrl(builder.toUriString()).filter(lbFunction).build();


//                    return WebClient.builder()
//                            .filter(lbFunction)
//                            .build().get().uri("http://say-hello/greeting")
//                            .retrieve().bodyToMono(String.class)
//                            .map(greeting -> String.format("%s, %s!", greeting, name));

                        // Make a request using WebClient
                        return webClient.get()
                                .retrieve()
                                .bodyToMono(String.class)
                                .flatMap(response -> {
                                    // Handle response if needed
                                    exchange.getResponse().getHeaders().add("Custom-Header", response);
                                    return chain.filter(exchange);
                                })
                                .onErrorResume(throwable -> {
                                    System.out.println("invalid access...!" + throwable.getMessage());
                                    // Handle exceptions from the downstream service
                                    // For example, log the error and return a specific response
                                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                    return exchange.getResponse().setComplete();
                                });

                } else {

                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
