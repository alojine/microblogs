# Microblogs application

####  Microblogs microservice made with Java, Spring Boot and Maven. Application allows users to perform CRUD operations to blogs as well as find most used words for blogging.

## Requirements
* [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
* [Maven 4](https://maven.apache.org/)

### Clone the repository:
```bash
git clone https://github.com/alojine/microblogs.git
```

### Build the project using Maven:
```
cd microblogs
mvn clean install
```

### Run the application:
```
mvn spring-boot:run
```


## Endpoints

### User Endpoints:
- **Create User:** `POST /api/v1/users`
- **Get User by ID:** `GET /api/v1/users/{userId}`

### Blog Endpoints:
- **Get All Blogs:** `GET /api/v1/blogs`
- **Get Blog by ID:** `GET /api/v1/blogs/{blogId}`
- **Get All User Blogs by ID:** `GET /api/v1/blogs/{userId}`
- **Create Blog:** `POST /api/v1/blogs`
- **Update Blog by ID:** `PUT /api/v1/blogs/{blogId}`
- **Delete Blog by ID:** `DELETE /api/v1/blogs/{blogId}`

### WordReport Endpoint:
- **Get Most Used Words Report:** `GET /api/v1/word/size={sizeOfReport}`

## Dependencies
-  Spring Boot Web Starter
- Spring Boot Starter for Testing
- Spring Boot Starter for Data JPA
- H2 Database
- Lombok
- Liquibase Core
- Mapstruct
- Log4j API, Core, and SLF4J Implementation
- JUnit Jupiter