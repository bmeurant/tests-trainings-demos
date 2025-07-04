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
- **JavaDoc:** All public classes and public methods must have a JavaDoc. Do not add Javadoc to private methods or fields unless strictly necessary. Javadoc should not simply reiterate what the code already clearly expresses.
- **Maven POM:** Use properties for dependency versions in the `pom.xml` file.

## 4. Testing Strategy

A three-tiered approach is required to ensure quality:

- **Unit Tests:** For isolated class logic (e.g., controllers, services, domain models), using mocks for dependencies.
- **API Integration Tests:** To validate the full HTTP contract (endpoints, serialization, error handling) using a running server context (`@SpringBootTest`). These are separate from Cucumber tests.
- **Behavior Tests (Cucumber):** To validate business scenarios by driving the application layer directly. The `.feature` files act as a contract and should not be tied to API implementation details.
