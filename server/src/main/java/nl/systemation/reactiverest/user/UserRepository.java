package nl.systemation.reactiverest.user;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author Jeffrey Spaan
 * @Company Systemation
 * @Created on Friday, August 28th, 2020
 */

@Repository // Mark this Java interface as the repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
