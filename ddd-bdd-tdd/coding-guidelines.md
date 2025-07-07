# Project Coding Guidelines

This document provides the development conventions and standards for this project.

## 1. Project Overview

- **Language:** Java 21
- **Framework:** Spring Boot
- **Architecture:** Domain-Driven Design (DDD). For more details, see `architecture.md`.

## 2. Development Process

- **BDD (Behavior-Driven Development):** All new features and bug fixes should be driven by BDD. This means starting with clear, executable specifications (features) written in Gherkin, followed by implementing the steps and then the underlying code.
- **TDD (Test-Driven Development):** For all code development, TDD should be applied. Write a failing test first, then write the minimum code to make the test pass, and finally refactor the code.

## 3. Development Conventions

- **Language and Documentation:** All code, comments, and documentation (including Markdown files) must be written in English.
- **Logging:** Add relevant logs using `org.slf4j.Logger` at appropriate levels (DEBUG for detailed flow, INFO for key events).
- **Assertion Messages:** All assertions in tests must include a descriptive message (e.g., `assertEquals(expected, actual, "Descriptive message");`).
- **Lombok:** Use Lombok but only when it is really necessary.
- **JavaDoc:** All public classes and public methods must have a JavaDoc. For methods defined in an interface, the Javadoc should reside solely in the interface. The implementing class should not duplicate this Javadoc; instead, it should use `@Override` and rely on the interface's documentation. Do not add Javadoc to private methods or fields unless strictly necessary. Javadoc should not simply reiterate what the code already clearly expresses.
- **Maven POM:** Use properties for dependency versions in the `pom.xml` file.

## 4. Testing Strategy

A comprehensive, multi-tiered testing strategy is adopted to ensure application quality and adherence to business requirements.

- **Unit Tests:**
    - **Goal:** To test the isolated logic of individual classes or small components. Each layer (domain, application service, controller, repository) is tested independently.
    - **Location:** Unit tests reside in the same package structure as the source code they test, but under `src/test/java/` (e.g., `src/main/java/com/example/Service.java` is tested by `src/test/java/com/example/ServiceTest.java`). This allows testing of `package-private` methods.
    - **Approach:** Dependencies are mocked to ensure isolation.
    - **Tools:** JUnit 5, Mockito, `MockMvc` (for controllers).
    - **Coverage:** Aim for 100% code coverage for new or modified code.

- **Behavior Tests (Cucumber - API-driven End-to-End):**
    - **Goal:** To validate the functional business scenarios from an external client's perspective, ensuring the entire application stack (including the API layer) works as expected for defined behaviors.
    - **Approach:** These tests interact with the application through its public REST API endpoints (making actual HTTP calls). The `.feature` files serve as the primary business contract and remain unchanged. The step definitions (`OrderManagementSteps`) will be adapted to make these API calls.
    - **Tools:** Cucumber, `@SpringBootTest`, `TestRestTemplate` or `WebTestClient`.

- **API Contract Tests:**
    - **Goal:** To rigorously validate the technical contract of the REST API, focusing on aspects like request/response schemas, specific HTTP status codes for various inputs (including validation errors), and edge cases at the API boundary. These tests complement the broader behavior tests by providing more granular, technical verification of the API.
    - **Approach:** These tests make direct HTTP calls to API endpoints, often with specific payloads, and assert on the exact structure of responses and HTTP status codes.
    - **Tools:** `@SpringBootTest`, `TestRestTemplate` or `WebTestClient`.
