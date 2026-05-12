# Project Guidelines

This repository contains the backend API for Algafood, developed using Java 17, Spring Boot and Maven.

## Code Style
- Uses Lombok (`@Data`, `@EqualsAndHashCode`) to reduce boilerplate in domain models.
- Jakarta Persistence (`jakarta.persistence`) annotations on entities; column names often have explicit `@Column` prefixes (e.g. `endereco_cep`).
- Follow existing package structure: `domain.model`, `domain.repository`, `domain.service`, `api.controller`.
- Exceptions in `domain.exception` extend `RuntimeException` and are used for service-layer errors.
- Controllers in `api.controller` return domain objects directly (no DTO layer currently).
- A small `infrastructure.repository` package contains custom Spring Data support.

## Architecture
- Traditional layered architecture: controllers → services → repositories.
- Entities live under `domain.model`; repositories are Spring Data JPA interfaces.
- Services (e.g. `CadastroCozinhaService`) encapsulate business rules and call repositories.
- `AlgafoodApiApplication` is the Spring Boot entry point.

## Build and Test
- Build and run with Maven: `./mvnw clean install`.
- Tests are standard JUnit 5 located under `src/test/java`; there are a few migration-based tests under `com.algaworks.algafood.migration`.
- Run tests with `./mvnw test` or use IDE test runner.

## Project Conventions
- Database identifiers use snake_case; some fields need manual `@Column` definitions.
- A Flyway migration directory (`src/main/resources/db/migration`) holds SQL scripts for schema setup.
- `Endereco` is implemented as an `@Embeddable` for reuse across entities.
- Use `@ManyToOne` for relationships; join columns are explicitly named.

## Integration Points
- No external services defined in repo; the data layer uses Spring Data JPA with an H2 in-memory database for tests and can be configured for external RDBMS via properties.
- Application properties (e.g. `application.properties`) configure datasource, datasource details often altered for local development.

## Security
- Security not implemented in this version; handle authentication/authorization if added.

Agents should focus on editing within the above conventions and ensure Maven commands are used for build/test.

---

*Feel free to request clarification or additional details.*