# Project Name

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Introduction

This is a sample project that demonstrates how to build a simple shopping cart system using Spring Boot and related technologies.


## API Design Philosophy

- **Restful Principles**: The API design strictly adheres to Restful principles, ensuring consistency and predictability. This makes it easy for developers to understand and use the APIs.
- **Resource-Oriented**: Each API endpoint represents a specific resource, and HTTP methods (GET, POST, PUT, DELETE) are used to perform CRUD operations on these resources.
- **Stateless**: APIs are designed to be stateless, allowing for better scalability and easier integration with caching mechanisms.


## Features

- Add items to the shopping cart
- View shopping cart details
- Remove items from the shopping cart

## Technology Stack

- **Java 17**: For core programming
- **Spring Boot**: As the primary backend framework, Spring Boot provides rapid microservices development capabilities. Its rich ecosystem simplifies daily development tasks such as integrating security frameworks and database access.  
- **Spring Data JPA**: Simplifies object-relational mapping (ORM), making database interactions more straightforward and maintainable.
- **H2**: An in-memory database used for development and testing purposes, providing fast and reliable data storage.
- **Maven**: For build management
- **Swagger**: For API documentation
- **JUnit**: For testing
- **Lombok**: Reduces boilerplate code by automatically generating getters, setters, constructors, and other common methods.
- **mapStructure**: Simplifies the mapping between objects, reducing the need for manual mapping code.



## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/q792821266/BookShopDemo.git
cd your-project-directory
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure the Database

Edit the `src/main/resources/application.properties` file to add your database connection information:

you can use the default H2 database configuration:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: ''
```
### 4. Start the Application
1. Run the application using Maven

```bash
mvn spring-boot:run
```

2. Run the application using Docker:  

build image：
```Bash
docker build --build-arg version=1.0.0 --build-arg artifactId=bookShopDemo -t bookShopDemo-image .
```

run container：
```Bash
docker run -p 8080:8080 -it --name bookShopDemo-container bookShopDemo-image
```

3. Run the application using your IDE

## Usage
### You can visit the springDOC page to see the API documentation
default : http://localhost:8080/swagger-ui/index.html

### Add Items to the Shopping Cart

Send a POST request to `/api/cart/add/{cartId}` with query parameters `bookId` and `quantity`.

```bash
curl -X POST http://localhost:8080/api/cart/add/1 -d "bookId=1" -d "quantity=1"
```

### View Shopping Cart Details

Send a GET request to `/api/cart/{cartId}`.

```bash
curl -X GET http://localhost:8080/api/cart/1
```

### Remove Items from the Shopping Cart

Send a DELETE request to `/api/cart/remove/{cartId}/{bookId}`.

```bash
curl -X DELETE http://localhost:8080/api/cart/remove/1/1
```

## DB Connection

**page**: http://localhost:8080/h2-console/  
**Driver Class**: org.h2.Driver  
**JDBC URL**: jdbc:h2:mem:testdb  
**userName**: sa  
**password**:  

## Testing

The project includes JUnit tests and integration tests to ensure functionality.
```bash
mvn test
```

```bash
mvn verify -Pintegration-tests
```



## Dependencies

The project depends on the following libraries:

- Spring Boot Starter Web
- Spring Data JPA
- Lombok
- mapStructure

## Contribution Guidelines

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch
3. Commit your changes
4. Submit a pull request

## License

This project is licensed under the [Apache License 2.0](LICENSE).

## Contact

For any questions or suggestions, please contact [jerrybin_0528@163.com] or submit an issue via GitHub.

---

**Note**: This README.md file is for reference. Adjust specific details as needed.
