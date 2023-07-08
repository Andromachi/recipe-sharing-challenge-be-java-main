# Recipe Challenge 
Security Module

The primary objective of this module is to provide secure access to the Recipe Challenge APIs using JWT based authentication.

The main components of the security module are:

1. `JwtAuthenticationFilter`: A filter that checks the HTTP Authorization header for a Bearer JWT. It validates the JWT and sets up Spring Security's context with a UserDetails instance associated with the JWT if it is valid.

2. `JwtService`: A service that provides methods for generating and validating JWTs.

3. `SecurityConfig`: A Spring Security configuration class that sets up HTTP security to require authentication and add the JWT filter. It whitelists certain endpoints.

4. `AuthenticationController`: A controller that provides endpoints for registering and logging into the system. The endpoints accept username and password, and return a JWT on successful login or registration.

5. `AuthenticationService`: A service that handles user registration and login. It interacts with UserRepository to manage user data and JwtService to generate JWTs.

6. `User`: A JPA entity class representing a user in the system, implementing Spring Security's UserDetails interface. It has a One-To-Many relationship with the `Token` entity class.

7. `Token`: A JPA entity class representing a JWT issued to a user.


### Database
The application uses an in-memory H2 database.

### Installation

1. Clone the repository.

2. Run `mvn clean install` to build the application.

3. Run `mvn spring-boot:run` to start the application.

The application will start running at `http://localhost:8080`.

## Using the Application

1. Register a new user by sending a POST request to `/api/v1/auth/register` with the following JSON body:
```json
{
  "firstName": "<first_name>",
  "lastName": "<last_name>",
  "email": "<email>",
  "password": "<password>",
  "role": "<role>"
}
```

2. Login by sending a POST request to `/api/v1/auth/login` with the following JSON body:
```json
{
  "email": "<email>",
  "password": "<password>"
}
```

3. The login endpoint will return a JWT token that can be used to authenticate with other APIs in the application. Include the JWT in the HTTP Authorization header as a Bearer token.

## Testing

The project contains integration tests that can be run with `mvn test`.

## Known Issues

1. JWT expiration is not currently handled. The `isTokenValid` method in `JwtService` should be updated to check the JWT expiration date.
2. Email is used as a username.
3. RecipeDTO is missing for simplicity. In a real world scenario it will be used to handle data more efficiently and keep our internal data exposure in check.
4. Caching is missing for weather service and recipes.
5. Pagination is missing.
6. Roles are dummy roles.
7. Email validation.

## API Documentation

API documentation is generated using Swagger. Once the application is running, you can access the Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```
There you can see all the schemas and the required attributes.

You can view and interact with the API endpoints through the Swagger UI.

When no items are found in a request 200 (OK) is returned.
