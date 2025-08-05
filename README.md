# Spring Practice Repository

This repository is a structured learning space for exploring the **Spring Framework and Spring Boot ecosystem**.  
It includes practical examples, architecture patterns, feature explorations, blog-ready code, and experimental projects — all organized by purpose.

Whether you're learning Spring from scratch or refining advanced concepts, this repo provides a categorized, scalable structure.

---

## 📁 Directory Overview

| Directory | Description |
|-----------|-------------|
| `playground/` | A quick space for running small Spring apps or trying out concepts without worrying about structure or best practices. |
| `core-concepts/` | Essential Spring fundamentals like Dependency Injection, Bean lifecycle, `@Configuration`, `@ComponentScan`, and environment management. |
| `spring-features/` | Hands-on projects focusing on individual Spring modules and features such as Spring Data JPA, Spring Security, Validation, Profiles, Events, etc. |
| `api-snippets/` | Practical REST controller examples, DTO mapping, exception handling, pagination, filtering, and response structure design. |
| `coding-recipes/` | Reusable solutions for common use cases like file upload/download, JWT auth, Redis caching, S3 integration, etc. |
| `patterns/` | Implementation of patterns in Spring apps: Service Layer, Repository Pattern, CQRS, Template Method, Observer, etc. |
| `architecture/` | Projects demonstrating application-level architecture like Hexagonal Architecture, Layered Architecture, DDD-style structuring, modular monoliths, etc. |
| `testing/` | Unit and integration tests using `JUnit`, `Mockito`, `Testcontainers`, `Spring Boot Test`, and WebMock. Includes best practices for mocking and test isolation. |
| `tooling/` | Build tools and setup: Gradle, Maven, Lombok, MapStruct, Spring DevTools, configuration properties, and profiles. |
| `labs/` | Experimental or hypothesis-driven projects—used to test behavior, edge cases, or compatibility (e.g., bean conflict, lazy-loading, proxy issues). |
| `internals/` | Deeper investigation into Spring internals like proxy creation (CGLIB, JDK), `ApplicationContext`, AOP weaving, and transaction propagation. |
| `benchmarks/` | Performance comparison between blocking vs non-blocking I/O, JPA vs JDBC, caching strategies, etc. |
| `blog-samples/` | Clean, production-quality sample code prepared for blog posts, talks, or educational content. Includes full explanation and documentation. |
| `utilities/` | CLI or task-based tools for things like database migration scripts, config generators, external integrations, or automation helpers. |
| `study-sources/` | Code written while following books, YouTube series, or courses (e.g., Spring in Action, Baeldung, Amigoscode). Organized per source. |
| `drafts/` | Scratch work or early ideas not yet finalized. Can later be promoted into the appropriate folder. |

---

## 🚀 Getting Started

Each folder is a self-contained project (usually a Spring Boot app).

To run a project:

```bash
cd spring-features/spring-data-jpa-demo
./gradlew bootRun
````

Or use IntelliJ / Eclipse to import the subproject and run it directly.

---

## 🧪 Development Tips

* Keep examples minimal and focused on **a single learning goal**.
* Prefer `application.yml` for config; isolate profiles (`dev`, `test`, `prod`) where needed.
* Use `@TestConfiguration` and `@MockBean` wisely in tests.
* Add a `README.md` or code comments in each project to summarize the purpose and what you learned.

---

## 📜 License

MIT License – you are free to use, share, or extend the examples for personal or educational purposes.
