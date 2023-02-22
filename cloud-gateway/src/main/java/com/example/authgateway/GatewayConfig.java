package com.example.authgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {


//    @Bean
//    public RouteLocator rsocketRouter(RouteLocatorBuilder routeLocatorBuilder, RSocketRequester.Builder rsocketBuilder, PathRoutePredicateFactory routePredicateFactory){
//        return routeLocatorBuilder.routes()
//                .route("rsocket_route",predicateSpec -> predicateSpec.path("/rsocket/test")
//                    .uri("rsocket://localhost:2111"))
//                .build();
//    }
}
