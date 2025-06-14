# Spring Security with Keycloak Integration

This module demonstrates a professional implementation of Spring Security with Keycloak integration, providing robust authentication and authorization capabilities.

## Features

- JWT-based authentication with Keycloak
- Role-based access control (RBAC)
- Public and secured endpoints
- Global exception handling
- Actuator endpoints for monitoring
- Comprehensive logging
- Environment-based configuration

## Prerequisites

- Java 23
- Maven
- Keycloak Server (version 22.0.0 or later)

## Configuration

### Environment Variables

The application uses the following environment variables:

- `KEYCLOAK_ISSUER_URI`: The issuer URI for your Keycloak realm (default: http://localhost:8080/realms/your-realm-name)
- `KEYCLOAK_JWK_SET_URI`: The JWK Set URI for your Keycloak realm (default: http://localhost:8080/realms/your-realm-name/protocol/openid-connect/certs)

### Keycloak Setup

1. Create a new realm in Keycloak
2. Create a new client with the following settings:
   - Client Protocol: openid-connect
   - Authentication flow: Standard flow and Direct access grants
   - Access Type: bearer-only
3. Create roles: `USER` and `ADMIN`
4. Create users and assign roles

## API Endpoints

### Public Endpoints

- `GET /api/v1/public/health`: Health check endpoint
- `GET /api/v1/public/info`: Public information endpoint

### Secured Endpoints

- `GET /api/v1/admin/dashboard`: Admin-only endpoint
- `GET /api/v1/user/profile`: User profile endpoint (requires USER role)
- `GET /api/v1/authenticated`: Endpoint for any authenticated user

### Actuator Endpoints

- `GET /api/actuator/health`: Application health information
- `GET /api/actuator/info`: Application information
- `GET /api/actuator/metrics`: Application metrics

## Security

The application implements the following security measures:

- JWT token validation
- Role-based access control
- Stateless session management
- CSRF protection
- Secure error handling

## Building and Running

```bash
# Build the application
mvn clean install

# Run the application
java -jar target/spring-security-with-keycloak-0.0.1-SNAPSHOT.jar
```

## Testing

To test the secured endpoints, you'll need to:

1. Obtain a JWT token from Keycloak
2. Include the token in the Authorization header:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Logging

Logging is configured with different levels:
- ROOT: INFO
- Spring Security: DEBUG
- Application: DEBUG

Logs can be found in the application's log files and console output.

## Error Handling

The application includes a global exception handler that handles:
- Authentication exceptions (401 Unauthorized)
- Access denied exceptions (403 Forbidden)
- Generic exceptions (500 Internal Server Error)

## Contributing

Please follow the existing code style and include appropriate tests with any contributions.

## License

This project is licensed under the MIT License - see the LICENSE file for details. 