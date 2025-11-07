package com.frostyfox.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocation(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://USER-SERVICE"))
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://USER-SERVICE"))
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("cart-service", r -> r
                        .path("/api/cart/**")
                        .uri("lb://CART-SERVICE"))
                .route("eureka-server", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static", r -> r
                        .path("/eureka/**")
                        .filters(f -> f.rewritePath("/eureka/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8761"))
                .build();
    }

    // Alternative: Using rewritePath to transform shorter paths
    // Allows calling /users instead of /api/users
    // @Bean
    // public RouteLocator customRouteLocation(RouteLocatorBuilder builder) {
    //     return builder.routes()
    //             .route("user-service", r -> r
    //                     .path("/users/**")
    //                     .filters(f -> f.rewritePath("/users(?<segment>.*)", "/api/users${segment}"))
    //                     .uri("lb://USER-SERVICE"))
    //             .route("product-service", r -> r
    //                     .path("/products/**")
    //                     .filters(f -> f.rewritePath("/products(?<segment>.*)", "/api/products${segment}"))
    //                     .uri("lb://PRODUCT-SERVICE"))
    //             .route("cart-service", r -> r
    //                     .path("/cart/**")
    //                     .filters(f -> f.rewritePath("/cart(?<segment>.*)", "/api/cart${segment}"))
    //                     .uri("lb://CART-SERVICE"))
    //             .route("eureka-server", r -> r
    //                     .path("/eureka/main")
    //                     .filters(f -> f.setPath("/"))
    //                     .uri("http://localhost:8761"))
    //             .route("eureka-server-static", r -> r
    //                     .path("/eureka/**")
    //                     .filters(f -> f.rewritePath("/eureka/(?<segment>.*)", "/${segment}"))
    //                     .uri("http://localhost:8761"))
    //             .build();
    // }
}
