package nl.systemation.reactiverest.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author Jeffrey Spaan
 * @Company Systemation
 * @Created on Friday, August 28th, 2020
 */

@Data // Lombok to create Getters and Setters
@AllArgsConstructor // Lombok to create All Args Constructor
@NoArgsConstructor // Lombok to create No Args Constructor
@Document(collection = "users") // MongoDB collection annotation and manually defined collection name
public class User {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private int age;

    private String email;

    @JsonIgnore // Ignore the password with JSON requests (security related)
    private String password;

}