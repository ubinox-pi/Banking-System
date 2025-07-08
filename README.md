## Project Overview

This project is a modular banking system built with Java 21 and Spring Boot. It follows a microservices or modular
monolith architecture, with each major feature implemented as an independent Gradle project.

### Modules

- **Neptune Bank**  
  Acts as the main application and API gateway. Built with Spring Boot WebFlux and Spring Cloud Gateway, it routes and
  orchestrates requests to the underlying services and provides resilience features.

- **banking_service**  
  Handles core banking operations and business logic. Uses Spring Boot and Vaadin for a possible web interface.

- **employee service**  
  Manages employee data, authentication, and related business logic. Built with Spring Boot, JPA for database access,
  and Spring Security.

- **user service**  
  Responsible for user management, authentication, and validation. Uses Spring Boot, JPA, and Spring Security.

- **Api-Requests**  
  Contains sample HTTP requests (e.g., userRegistration.http) for testing and API documentation.

### Technology Stack

- Java 21
- Spring Boot (3.4.5/3.5.0)
- Spring Cloud Gateway
- Spring Security
- Spring Data JPA
- Vaadin (for UI in banking_service)
- Gradle (build tool)

### Getting Started

1. **Clone the repository**  
   `git clone <repo-url>`

2. **Build each module**  
   Navigate into each service directory and run:  
   `./gradlew build`

3. **Run the services**  
   Each service can be started independently using:  
   `./gradlew bootRun`

4. **API Testing**  
   Use the files in `Api-Requests` to test endpoints with tools like VS Code REST Client or Postman.

## License

This project is licensed under a custom Non-Commercial, Attribution, Share-Alike license.  
See the LICENSE file for full terms.  
© 2025 Ramjee Prasad. All rights reserved.
