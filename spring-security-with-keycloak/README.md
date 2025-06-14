# Spring Boot with Keycloak Integration

This project demonstrates how to integrate Spring Boot with Keycloak for authentication and authorization.

## Prerequisites

- Java 17 or higher
- Maven
- Docker (for running Keycloak)

## Step-by-Step Setup Guide

### 1. Start Keycloak Server

```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

Access Keycloak Admin Console at [http://localhost:8080/admin](http://localhost:8080/admin)
- Username: `admin`
- Password: `admin`

### 2. Create a Realm

1. Go to [http://localhost:8080/admin](http://localhost:8080/admin)
2. Click "Create Realm"
3. Name: `myrealm`
4. Click "Create"

### 3. Create a Client

#### For Backend Integration (Confidential Client)

1. In your realm, go to **Clients** → **Create client**
2. Configure with these settings:
   - Client ID: `spring-boot-client`
   - Client Protocol: `openid-connect`
   - Root URL: `http://localhost:8081`
   - Access Type: `confidential`
   - Service Accounts Enabled: `Yes`
   - Direct Access Grants Enabled: `Yes`
   - Valid Redirect URIs: `http://localhost:8081/*`
   - Web Origins: `http://localhost:8081`

3. After creation:
   - Go to **Credentials** tab
   - Copy the **Secret** value
   - Go to **Service Account Roles**
   - Under Realm Roles, add:
     - `realm-management`
     - `manage-users`
     - `view-users`

### 4. Create Roles

1. Go to **Realm Settings** → **Roles**
2. Click "Add Role"
3. Create these roles:
   - Name: `user`
     - Description: Regular user role
   - Name: `admin`
     - Description: Administrator role

### 5. Create a Test User

1. Go to **Users** → **Add user**
2. Fill in:
   - Username: `testuser`
   - Email: `test@example.com`
   - First Name: `Test`
   - Last Name: `User`
3. Click "Save"
4. Go to **Credentials** tab:
   - Set password: `password123`
   - Turn OFF "Temporary"
5. Go to **Role Mappings**:
   - Assign `user` role

## Spring Boot Configuration

### 1. Application Properties

Configure your `application.yml`:

```yaml
server:
  port: 8081
  servlet:
    context-path: /api

spring:
  application:
    name: spring-security-with-keycloak
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: ${KEYCLOAK_ISSUER_URI:http://localhost:8080/realms/myrealm}
          jwk-set-uri: ${KEYCLOAK_JWK_SET_URI:http://localhost:8080/realms/myrealm/protocol/openid-connect/certs}

keycloak:
  auth-server-url: ${KEYCLOAK_URL:http://localhost:8080}
  realm: ${KEYCLOAK_REALM:myrealm}
  resource: ${KEYCLOAK_CLIENT_ID:spring-boot-client}
  credentials:
    secret: ${KEYCLOAK_CLIENT_SECRET:your-client-secret}

logging:
  level:
    root: INFO
    org.springframework.security: DEBUG
    org.springframework.web: DEBUG
    com.codestorykh: DEBUG
```

### 2. Project Structure

```
src/main/java/com/codestorykh/keycloak/
├── config/
│   ├── KeycloakConfig.java
│   ├── KeycloakProperties.java
│   └── SecurityConfig.java
├── controller/
│   └── UserRegistrationController.java
├── dto/
│   └── UserRegistrationDto.java
├── exception/
│   └── KeycloakException.java
└── service/
    ├── KeycloakService.java
    └── KeycloakUserService.java
```

### 3. Key Components

#### KeycloakProperties
```java
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String authServerUrl;
    private String realm;
    private String resource;
    private Credentials credentials;

    public static class Credentials {
        private String secret;
        // getters and setters
    }
    // getters and setters
}
```

#### KeycloakConfig
```java
@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(KeycloakProperties properties) {
        return KeycloakBuilder.builder()
            .serverUrl(properties.getAuthServerUrl())
            .realm(properties.getRealm())
            .clientId(properties.getResource())
            .clientSecret(properties.getCredentials().getSecret())
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .build();
    }
}
```

## API Endpoints

### Public Endpoints

#### Register User
```http
POST /api/v1/public/register
Content-Type: application/json

{
    "username": "userz",
    "email": "userz@example.com",
    "password": "password123",
    "firstName": "New",
    "lastName": "User"
}
```

### Secured Endpoints

#### User Profile
```http
GET /api/v1/user/profile
Authorization: Bearer <token>
```

#### Admin Dashboard
```http
GET /api/v1/admin/dashboard
Authorization: Bearer <token>
```

## Security Configuration

The application uses Spring Security with Keycloak JWT authentication:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/v1/public/**").permitAll()
                .requestMatchers("/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/v1/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())
                )
            );
        return http.build();
    }
}
```

## Running the Application

1. Start Keycloak:
```bash
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

2. Run Spring Boot application:
```bash
./mvnw spring-boot:run
```

3. Test the registration endpoint:
```bash
curl -X POST http://localhost:8081/api/v1/public/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "userz",
    "email": "userz@example.com",
    "password": "password123",
    "firstName": "New",
    "lastName": "User"
  }'
```

## Troubleshooting

### Common Issues

1. **401 Unauthorized**
   - Check client secret in application.yml
   - Verify client configuration in Keycloak
   - Check realm settings

2. **403 Forbidden**
   - Verify user roles
   - Check client permissions
   - Validate token claims

3. **404 Not Found**
   - Check endpoint URLs
   - Verify realm name
   - Check client ID

### Environment Variables

You can override default settings using environment variables:

```bash
export KEYCLOAK_URL=http://localhost:8080
export KEYCLOAK_REALM=myrealm
export KEYCLOAK_CLIENT_ID=spring-boot-client
export KEYCLOAK_CLIENT_SECRET=your-client-secret
```

## Best Practices

1. **Configuration Management**
   - Use environment variables for sensitive data
   - Type-safe configuration with @ConfigurationProperties
   - Separate configuration by environment

2. **Security**
   - Use confidential clients for backend services
   - Implement proper role-based access control
   - Secure sensitive endpoints

3. **Error Handling**
   - Custom exceptions for Keycloak operations
   - Proper error messages
   - Exception chaining

4. **Code Organization**
   - Interface-based design
   - Separation of concerns
   - Modular architecture

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 