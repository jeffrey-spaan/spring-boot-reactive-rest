package nl.systemation.reactiverest.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @Author Jeffrey Spaan
 * @Company Systemation
 * @Created on Friday, August 28th, 2020
 */

@Configuration // UserRouter is a Spring Boot Configuration file
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> routerFunction(UserHandler handler) {
        return RouterFunctions.route()
                .path("/api/users", b1 -> b1 // /api/users is the link to the API access point
                        .nest(accept(APPLICATION_JSON), b2 -> b2
                                .GET("", handler::getAllUsers)
                                .GET("/{id}", handler::getUserById)
                                .POST("", handler::createUser)
                                .PUT("", handler::updateUser)
                                .DELETE("/{id}", handler::deleteUser)))
                .build();
    }

}