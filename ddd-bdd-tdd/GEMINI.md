# Gemini Project Configuration

This file provides context for the Gemini AI assistant to understand the project's technical details and conventions.

- **Gemini Responses:** All responses from the Gemini assistant must be in French.

## Project Overview

- **Language:** Java 24
- **Framework:** Spring Boot
- **Architecture:** Domain-Driven Design (DDD)
- **BDD (Behavior-Driven Development):** All new features and bug fixes should be driven by BDD. This means starting
  with clear, executable specifications (features) written in Gherkin, followed by implementing the steps and then the
  underlying code.
- **TDD (Test-Driven Development):** For all code development, TDD should be applied. Write a failing test first, then
  write the minimum code to make the test pass, and finally refactor the code.

## Development Conventions

- **Code and Comments:** All code and comments should be written in English.
- **Logging:** Add relevant logs using `org.slf4j.Logger` at appropriate levels (DEBUG for detailed flow, INFO for key events).
- **Assertion Messages:** All assertions in tests must include a descriptive message (e.g., `assertEquals(expected, actual, "Descriptive message");`).
- **Testing:** The project uses Cucumber for BDD (Behavior-Driven Development) tests.
- **Lombok:** Use Lombok but only when it is really necessary.
