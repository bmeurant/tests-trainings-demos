# REST API Architecture

This document outlines the architectural decisions and implementation conventions for the REST API of the "Book Order Manager" project.

## 1. General Philosophy

The API must be a "thin" facade over the application layer. Its sole responsibility is to handle the HTTP protocol (requests, responses, status codes) and to translate data between the JSON format and the application's DTOs. **No business logic should reside in the REST interface layer.**

## 2. Versioning

For the initial version, **no URL versioning** (e.g., `/api/v1`) is implemented. The priority is to deliver a stable and well-defined first version. Versioning will be introduced when breaking changes become necessary.

## 3. Structure and Responsibilities

### Data Transfer Objects (DTOs)

- **Location:** All DTOs (requests and responses) must be placed in the `io.bmeurant.bookordermanager.application.dto` package.
- **Justification:** This makes them reusable by other types of interfaces and respects the Dependency Rule (inner layers like `application` must not depend on outer layers like `interface`).
- **Rule:** DTOs are pure data structures. They must not contain any annotations or dependencies related to a web framework.

### DTO <-> Domain Entity Mapping

- **Responsibility:** The **Application Service** (e.g., `OrderService`) is responsible for converting DTOs into domain entities, and vice-versa.
- **Typical Flow:** The controller receives a `RequestDTO`, passes it directly to the service. The service transforms it into an entity, executes the business logic, and returns a `ResponseDTO`.
- **Justification:** Centralizes the logic for validation and construction of domain objects within the application layer, ensuring consistency and reusability.

### Controllers

- **Location:** `io.bmeurant.bookordermanager.interface.rest`.
- **Rule:** A controller must be "thin". An endpoint method should be limited to:
    1. Receiving the HTTP request.
    2. Validating the request's syntax (`@Valid`).
    3. Calling **one and only one** application service method.
    4. Returning the corresponding HTTP response.

## 4. Error Handling

Error handling is centralized via a `@ControllerAdvice` class.

- **Client Errors (4xx):** Business exceptions (e.g., `BookNotFoundException`) must result in a 4xx response. The JSON response body must be a standardized `ErrorResponse` object containing a **precise and helpful** message for the API user.
- **Server Errors (5xx):** Any other unhandled exception must result in a 500 response. The response body must contain a **generic** message ("An internal error occurred") to avoid leaking implementation details. The full exception must be logged internally with the `ERROR` level.

## 5. Testing Strategy

A three-tiered approach is adopted to ensure quality:

1.  **Unit Tests:**
    -   **Goal:** Test the isolated logic of a single class (e.g., the controller).
    -   **Tools:** JUnit 5, Mockito, `MockMvc`. Dependencies (like the service) are mocked.
2.  **API Integration Tests:**
    -   **Goal:** Validate the API's technical contract end-to-end (HTTP, JSON, status codes).
    -   **Tools:** `@SpringBootTest` (with a real Spring context), `TestRestTemplate` or `WebTestClient`.
    -   **Note:** These tests are distinct from Cucumber tests and focus on the HTTP layer.
3.  **Behavior Tests (Cucumber):**
    -   **Goal:** Validate the functional business scenarios.
    -   **Principle:** They drive the application layer (`OrderService`) directly. They remain agnostic of the REST API. The client contract defined in the `.feature` files remains intact.
