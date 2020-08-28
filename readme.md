# Spring Boot Reactive REST Example
*Created on: 28-08-2020*<br />
*Last updated on: 28-08-2020*

**Contributors:**<br />
Jeffrey Spaan *(Solution Consultant @ Systemation, the Netherlands)*

Welcome developer. This is the first step in developing a **Reactive RESTful** application.<br />
In this tutorial we will cover the basics in creating a non-blocking I/O **Spring Boot back-end** with a **MongoDB** database.

Reactive programming provides an elegant solution when it comes to specific types of high-load or multi-user applications:
* Social networks
* Games
* Audio and video applications

*Only use Reactive programming in case of 'live data', high-load or large number of requests to be processed.*

### What is Spring Boot?
**Spring Boot** is a Java-Based framework used, and not limited to, creating micro service applications.<br />
Configuring the back-end of your application is easier than ever with the use of Spring Boot dependencies which enables you to simply select the required dependencies to quickly setup the back-end of your application.<br />

## Getting Started
Create your project folder on your hard-drive, for this example we will use: ```spring-boot-reactive-rest\server```<br /><br />
![Project folder](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/01-folderstructure.JPG)

<br />

In your browser, navigate to: [https://start.spring.io](https://start.spring.io)<br />
* **Project:** ```Maven Project```<br />
* **Language:** ```Java```<br />
* **Spring Boot (version):** We will use the latest stable version: ```2.3.3```<br />
* **Group:** this is your internet domain, backwards. For Systemation we will use: ```nl.systemation```<br />
* **Artifact:** this is the name of your project. For this project we will use: ```reactiverest```<br />
* **Description:** this is a short description about your project. For this project we will use: ```Demo Project Spring Boot Reactive REST```<br />
* **Packaging:** ```JAR```<br />
* **Java:** (we will use the latest version): ```11```<br />
* **Dependencies:** ```Lombok, Spring Reactive Web, Spring Data Reactive MongoDB```

To create the Spring Boot application, click on: ![Generate Spring Boot Project](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/03-generate.JPG)

![Spring Boot Dependency Selection](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/02-startspringio.JPG)

The download of the ZIP file will start automatically. Open the ZIP file and extract the project files in your project folder.<br />

![Unzip project in project folder](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/05-unzip.JPG)

## Dependencies information
### Lombok
Project Lombok is a Java library that automatically plugs into the editor and build tools, spicing up Java.
Never write another getter or equals method again. Ideal to reduce boilerplate code.<br />
Want to know more about the specific Lombok annotations which we will use in our project? Visit: https://projectlombok.org/ <br />
**Tip:** Do not forget to install the Lombok plugin in your IDE.

### Spring Reactive Web
Spring Framework 5 adds a new ```spring-web-reactive module``` that supports the same ```@Controller``` programming model as Spring MVC but executed on a reactive, non-blocking engine.

### Spring Data Reactive MongoDB
In previous examples we've used an SQL database. To expand your knowledge and introduce you to new possibilities, we will use a MongoDB NoSQL Database in this tutorial.<br />
In case you still prefer to use an SQL database, this is possible by replacing ```Spring Data Reactive MongoDB``` with the ```Spring Data R2DBC``` dependency.<br />
Read more about Reactive Relational Database Connectivity on: https://spring.io/projects/spring-data-r2dbc

### POM.xml
A Project Object Model or POM is the fundamental unit of work in Maven.<br />
It is an XML file that contains information about the project and configuration details used by Maven to build the project.<br />
<br />
The following listing shows the ```POM.xml``` file that is created when you've chosen a Maven project:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>nl.systemation</groupId>
	<artifactId>reactiverest</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>reactiverest</name>
	<description>Demo Project Spring Boot Reactive REST</description>

	<properties>
		<java.version>11</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>io.projectreactor</groupId>
			<artifactId>reactor-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
<hr>

## Create Java files
Now let's add some code to make a fully functioning **Spring Boot Reactive RESTful servlet.**<br />
To do so, following files have to be created:
* Document
* Router
* Handler
* Repository
* Configuration

The files have to be added according the best practices **Spring Boot Architecture guidelines**.

```bash
server\src\main\java\nl\systemation\reactiverest
└──user
   ├──User.java
   ├──UserHandler.java       
   ├──UserRepository.java
   ├──UserRouter.java
   └──WebConfig.java
```
<hr>

### Spring Boot Document
Let's add a document to enable Spring Boot to persist data to/from the MongoDB database.<br />
To do so, add a document named: ```User.java```<br />
Annotate the document with the **Spring Boot MongoDB** persistence ```@Document``` annotation.<br />
This annotation will instruct Spring Boot that the class is a MongoDB document and is mapped to a MongoDB database table.<br /><br />
We will also use some Lombok annotations to reduce boilerplate code and ensure our code will remain easy to read.

```java
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
```
<hr>

### Spring Boot Router
In order to access the API via the front-end (HTTP request), an access point is mandatory.<br />
Let's add a router named: ```UserRouter.java``` <br/>
```@Configuration```: This is a **Spring Boot** annotation which marks the Java class as a Spring Boot Configuration file;<br />

```java
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
```
<hr>

### Spring Boot Handler
In order to process the incoming *Router* requests, business logic is written in the **Spring Boot Handler** layer.<br />
Via the Handler layer, commands will be given to the repository to transfer data to/from the MongoDB database.<br />
```@Component```: This is a **Spring Boot** annotation which marks the Java class as a *Spring Boot component file*;<br />

```java
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
```
<hr>

### Spring Boot Repository
The repository will handle the commands which it in turn receives from the handler layer.<br />
The repository can implement a variety of commands from various repositories.<br />
In this example we will implement the ```ReactiveMongoRepository```, which has some pre-configured methods which we use in our handler layer, such as:
* **findAll** *(gets all data from a specific database table)*
* **findById** *(gets data from a specific row, which is found via the provided ID)*
* **save** *(saves data to the database table)*
* **deleteById** *(deletes data from a specific row, which is found via the provided ID)*

```@Repository```: This is a **Spring Boot** persistence annotation which marks the Java interface as the Repository;

```java
@Repository // Mark this Java interface as the repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
```
<hr>

### Download MongoDB
In order for us to use the MongoDB database, download MongoDB via: https://www.mongodb.com/try/download/community <br />
Also install MongoDB Compass Community *(during the installation of MongoDB community you will be asked if you'd like to install Compass Community).* <br />
Once installed, start-up **MongoDBCompassCommunity**.
<hr>

### Download Postman
In order for us to test the HTTP requests, download Postman via: https://www.postman.com/downloads/ <br />
Once installed, start-up Postman.
<hr>

## Start Server

Open the terminal window, and execute following commands:<br />
*Note, each command line will be indicated with the **$** symbol.*
```bash
$ cd server
$ mvn spring-boot:run
```

Once the server has started, we will test our application with Postman.<br />
Postman enables us to test the HTTP requests, which are pre-set in the **Spring Boot Router layer**.<br />
<br />
First let's test the **POST HTTP request**, as our database is currently empty.<br />
In order for us to add data to the database, we have to provide a body with the specified data which we would like to add:
<br />

![Postman HTTP Post request](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/06-postmanpost.JPG)

<br />

Now that data has been added to our database, let's test the **GET all users HTTP request**:
<br />

![Postman HTTP Get all request](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/07-postmangetall.JPG)

<br />

Now let's test the **GET per ID HTTP request**:
<br />

![Postman HTTP Get by ID request](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/08-postmangetid.JPG)

<br />

Let's also test the **PUT HTTP request**.<br />
In order for us to change data in the database, we require to provide a body with the specified data which we would like to change:
<br />

![Postman HTTP Put request](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/09-postmanput.JPG)

<br />

Finally, let us test the **DELETE per ID HTTP request**.<br />
<br />
![Postman HTTP Delete by ID request](https://raw.githubusercontent.com/jeffrey-spaan/spring-boot-reactive-rest/master/images/10-postmandelete.JPG)

All requests have been correctly processed by the application. <br />
Good work!

### Congratulations!
You have successfully created **your first Spring Boot Reactive REST application** <br />
With this we have come to the end of this tutorial.<br />
I hope you've enjoyed the process, please give it a **Star** in case you liked it.<br />
Thank you for your support!