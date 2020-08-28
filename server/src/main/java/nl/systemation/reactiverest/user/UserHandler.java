package nl.systemation.reactiverest.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @Author Jeffrey Spaan
 * @Company Systemation
 * @Created on Friday, August 28th, 2020
 */

@RequiredArgsConstructor // Lombok to create the Required Args Constructor
@Component // UserHandler is a Spring Boot Component file
public class UserHandler {

    // Call the UserRepository
    private final UserRepository repository;

    // GET request to find all users
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {

        // Initiation node to get data from the repository
        Flux<User> users = this.repository.findAll();

        // New node to get data from the repository once ready
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(users, User.class);
    }

    // GET request to find single user by ID
    public Mono<ServerResponse> getUserById(ServerRequest request) {

        // Initiation node to get data from the repository
        Mono<User> user = this.repository.findById(request.pathVariable("id"));

        // New node to get data from the repository once ready
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(user, User.class)
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }


    // POST request to create user
    public Mono<ServerResponse> createUser(ServerRequest request) {

        // Initiation node to check data from the router layer
        Mono<User> newUser = request.bodyToMono(User.class);

        // New node to execute the save method
        return newUser.flatMap(user ->
                ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.repository.save(user), User.class));
    }

    // UPDATE request to update user
    public Mono<ServerResponse> updateUser(ServerRequest request) {

        // Initiation node to check data from the router layer
        Mono<User> updatedUser = request.bodyToMono(User.class);

        // New node to execute the save method
        return updatedUser.flatMap(user ->
                ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(this.repository.save(user), User.class));
    }

    // DELETE request to delete user
    public Mono<ServerResponse> deleteUser(ServerRequest request) {

        // Initiation node to delete data via the repository
        Mono<Void> deleteUser = this.repository.deleteById(request.pathVariable("id"));

        // New node to confirm data is deleted
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(deleteUser, Void.class);
    }

}