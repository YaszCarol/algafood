# Claude Agent Guidelines

This file provides concise instructions to help Claude-based agents work effectively in the Algafood API repository.

## Overview
- Java 17, Spring Boot 3.x, Maven-based backend API.
- Layered architecture: `api.controller` → `domain.service` → `domain.repository`.
- Lombok used heavily in domain models; entities use Jakarta Persistence annotations.

## Key conventions
- **Packages**: maintain structure under `com.algaworks.algafood` with subpackages `api.controller`, `domain.model`, `domain.repository`, `domain.service`, `infrastructure.respository`.
- **Entities**: `@Entity` classes live in `domain.model`. Use snake_case column names, often with explicit `@Column`.
- **Repositories**: Spring Data interfaces in `domain.repository`. Custom behaviors go in `infrastructure.repository`.
- **Services**: Business logic sits in `domain.service`. Exceptions (`domain.exception`) extend `RuntimeException` and signal service-level errors.
- **Controllers**: Return domain objects directly (no DTOs currently).

## Build & Test
- Build with Maven wrapper: `./mvnw clean install`.
- Tests in `src/test/java`; migration tests under `com.algaworks.algafood.migration`.
- Run `./mvnw test` to execute tests.

## Database / Migrations
- Schema managed via Flyway. SQL migrations located in `src/main/resources/db/migration`.
- H2 in-memory database used for test execution; external RDBMS configured via `application.properties`.

## Development Notes
- Follow existing code style; replicate service and repository patterns when adding features.
- Use `@ManyToOne` with explicit `@JoinColumn` names for relations.
- `Endereco` is an `@Embeddable` reused across entities.

## Agent Behavior
- Prefer editing files under source dirs rather than generating new packages.
- Confirm build compiles after changes by running `./mvnw clean install`.
- Add tests for new functionality; mimic existing test conventions.
- Avoid introducing security frameworks; none are configured currently.

Agents should treat this repo as a single-module Spring Boot app with standard Maven layout.

---

Feel free to ask for more details or to update this guidance when new patterns emerge.