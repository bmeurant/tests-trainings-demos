# Book Order Manager

This project is a sample application demonstrating the principles of Domain-Driven Design (DDD), Behavior-Driven Development (BDD), and Test-Driven Development (TDD) within a Spring Boot ecosystem.

## 1. Project Overview

The application manages a simple book ordering system, encompassing concepts like a book catalog, inventory management, and customer orders.

Its primary purpose is to serve as a learning and demonstration platform for building robust and well-architected Java applications.

### Key Architectural Documents

For a deeper understanding of the project's design and conventions, please refer to the following documents:

- **Overall Architecture:** [`architecture.md`](./architecture.md)
- **REST API Architecture:** [`architecture-api.md`](./architecture-api.md)
- **Coding Guidelines:** [`coding-guidelines.md`](./coding-guidelines.md)

## 2. Prerequisites

- Java 21
- Apache Maven 3.8+

## 3. Building and Running the Application

### Full Build (Recommended)

To build the project, run all tests (unit and integration), and install the application package into your local Maven repository, use the standard command:

```bash
./mvnw clean install
```

### Running Specific Tests

To support a faster development cycle, you can run different test suites independently:

- **Run only Unit Tests:** This is very fast as it does not load the Spring context.
  ```bash
  ./mvnw test
  ```

- **Run Unit and Integration Tests:** This command runs the full test suite, including API integration tests that require a running Spring context.
  ```bash
  ./mvnw verify
  ```

### Running the Application

Once the application is packaged (e.g., by running `mvn install` or `mvn package`), you can run it with:

```bash
java -jar target/book-order-manager-*.jar
```

The application will start, and the REST API will be available. By default, the API documentation (Swagger UI) can be accessed at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 4. Development Utilities

### Update Dependency Versions

To update dependency versions in the `pom.xml` to their latest stable releases, you can use the following command. It is configured to safely ignore snapshots and pre-release versions (alpha, beta, RC).

```bash
mvn versions:update-properties \
  -DgenerateBackupPoms=false \
  -DallowSnapshots=false \
  -DprocessProperties=true \
  -DautoLinkItems=true \
  -DignoredVersions='.*-RC.*:.*-M.*:.*-alpha.*:.*-beta.*:.*-SNAPSHOT.*'
```