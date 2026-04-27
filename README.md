# Spring Boot JWT Authentication Library
A lightweight, reusable Java library for securing REST APIs using JSON Web Token (JWT) authentication with Spring Security. Designed for stateless, token-based authentication in Spring Boot microservices and monolithic applications.
## Features
*  Stateless JWT-based authentication
*  Supports Authorization: Bearer <token> header & ?token= query parameter
* Custom user claims embedded in tokens (JwtUserDetails)
* Role-based access control integration (JWTGrantedAuthority)
* Configurable token expiration & secret key
* Zero configuration Spring Security filter (JwtRequestFilter)
* Easy integration into existing Spring Boot projects


Java 1.8+ Core language
Spring Boot 2.6.5 Application framework
Spring Security 5.3.4+
Authentication & authorization
JJWT 0.9.1
JWT creation & parsing
Lombok
1.18.22
Boilerplate reduction
ModelMapper
3.0.0
Object mapping utility
Maven
3.6+
Build & dependency management

## Installation & Setup
### Prerequisites

java -version   # Must be 1.8 or higher
mvn -version    # Must be 3.6+


## Build & Install to Local Maven Repository
git clone <your-repo-url>
cd jwt-authentication
mvn clean install -DskipTests

##Add Dependency to Your Spring Boot Project
In your main project's pom.xml:

<dependency>
    <groupId>com.jwt-authentication</groupId>
    <artifactId>jwt-authentication</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

## Refresh Maven dependencies in your IDE or run:

mvn clean compile

## Set Environment Variable
# Linux / macOS
export JWT_SECRET="your-super-secret-key-min-32-chars-long!"

# Windows CMD
set JWT_SECRET=your-super-secret-key-min-32-chars-long!

# Windows PowerShell
$env:JWT_SECRET="your-super-secret-key-min-32-chars-long!"

 Security Note: Use a strong, randomly generated secret (minimum 32 characters for HS512).


# Integration Guide
###Register JWT Filter in Security Config
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Validate credentials (your business logic)
        JwtUserDetails userDetails = userService.authenticate(request.getUsername(), request.getPassword());
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 2. Generate JWT using library utility
        String token = JwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }
}


# Access Authenticated User in Protected Endpoints
 @RestController
@RequestMapping("/api")
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<?> getProfile() {
        JwtUserDetails user = (JwtUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        
        return ResponseEntity.ok(Map.of(
            "username", user.getUsername(),
            "email", user.getEmail(),
            "roles", user.getAuthorities()
        ));
    }
}

# Testing
# 1. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"secret123"}'

# 2. Use returned token to access protected route
curl -X GET http://localhost:8080/api/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."


#  Library Structure
   com.jwt_authentication.jwt_authentication/
├── constant/
│   └── JWTConstant.java          # Secret key & claim key constants
├── filter/
│   └── JwtRequestFilter.java     # Intercepts & validates requests
├── model/
│   ├── JwtUserDetails.java       # Implements Spring UserDetails
│   └── JWTGrantedAuthority.java  # Custom role/authority model
└── util/
    ├── JwtTokenUtil.java         # Token generation & validation
    └── MapperUtil.java           # JSON/Object mapping helpers
