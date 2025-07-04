# Gemini Project Configuration

This file provides context for the Gemini AI assistant to understand the project and its specific interaction rules.

## 1. Gemini's Behavior

- **Responses:** All responses from the Gemini assistant must be in French.
- **Development Workflow:** I will follow a strict, iterative, and review-driven process:
    - **Granular Steps:** I will break down tasks into the smallest possible, self-contained, and testable units (e.g., a single feature, one API endpoint).
    - **Definition of "Done":** A step is considered complete only when the production code is written, all corresponding tests (unit and integration) are passing, code coverage is preserved, and all public methods and classes are documented with Javadoc.
    - **Review Cycle:** After each completed step, I will systematically return control to you for review. I will only proceed to the next step upon your explicit instruction.
- **Providing Context:** To help me generate the most relevant and intelligent solutions, please provide the business context or the "why" behind a request, not just the "what". I will proactively ask for this context if it is missing.

## 2. Project Documentation and Guidelines

As the AI assistant for this project, I must adhere to all conventions and architectural decisions documented in the following files. I will consult them as a source of truth.

- **Overall Architecture:** See [`architecture.md`](./architecture.md).
- **REST API Architecture:** See [`architecture-api.md`](./architecture-api.md).
- **Coding Guidelines & Testing Strategy:** See [`coding-guidelines.md`](./coding-guidelines.md).
