# AlgaFood API

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.4-blue)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.1-blue)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)

## 📋 Descrição

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot, representando o backend de um sistema de delivery de comida semelhante ao iFood. O foco é exclusivamente no desenvolvimento backend, implementando conceitos avançados de APIs REST, boas práticas de arquitetura e recursos utilizados em aplicações reais de produção.

**Status do Projeto:** Em desenvolvimento ativo. Nem todos os endpoints estão finalizados, e novas funcionalidades estão sendo implementadas gradualmente.

## 📈 Conceitos Aplicados

- APIs RESTful
- CRUD REST
- Injeção de dependências (IoC)
- Spring Data JPA
- Hibernate e ORM
- Modelagem de domínio
- Relacionamentos entre entidades (One-to-Many, Many-to-One, Many-to-Many)
- Separação em camadas (Controller → Service → Repository)
- Padrões REST (verbos HTTP, códigos de status)
- Tratamento de exceções customizadas
- Modelagem de erros da API
- Versionamento de banco com Flyway
- Persistência de dados
- Serialização JSON
- Maven para gerenciamento de dependências
- Docker para containerização
- Recursos voltados para produção
- Boas práticas de desenvolvimento backend

## 🔄 Fluxo

Em desenvolvimento.

## 🛠 Stack

| Tecnologia | Versão |
|------------|--------|
| Java | 17 |
| Spring Boot | 4.0.2 |
| Spring Data JPA | - |
| Hibernate | - |
| MySQL | 8.1 |
| Flyway | - |
| Maven | 3.9.4 |
| Docker | - |
| Lombok | - |

## 📁 Estrutura de Pastas Relevante

```
algafood-api/
├── src/main/java/com/algaworks/algafood/
│   ├── api/controller/     # Controllers REST
│   ├── domain/
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Interfaces de repositório
│   │   ├── service/        # Lógica de negócio
│   │   └── exception/      # Exceções customizadas
│   └── infrastructure/     # Implementações customizadas
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/       # Scripts Flyway
├── docker-compose.yml
├── docker-compose.dev.yml
└── pom.xml
```

## 🚀 Como Rodar

### Local
```bash
./mvnw spring-boot:run
```
API em: `http://localhost:8080`

### Com Docker
```bash
docker-compose up --build  # Produção
docker-compose -f docker-compose.dev.yml up --build  # Desenvolvimento
```

## 🔗 Endpoints

**Em desenvolvimento.** Endpoints implementados até o momento:

### Cozinhas
- `GET /cozinhas` - Listar todas
- `GET /cozinhas/{id}` - Buscar por ID
- `POST /cozinhas` - Criar nova
- `PUT /cozinhas/{id}` - Atualizar
- `DELETE /cozinhas/{id}` - Remover

### Restaurantes
- `GET /restaurantes` - Listar todas
- `GET /restaurantes/{id}` - Buscar por ID
- `POST /restaurantes` - Criar novo
- `PUT /restaurantes/{id}` - Atualizar
- `PATCH /restaurantes/{id}` - Atualização parcial
- `DELETE /restaurantes/{id}` - Remover

### Estados
- `GET /estados` - Listar todas
- `GET /estados/{id}` - Buscar por ID
- `POST /estados` - Criar novo
- `PUT /estados/{id}` - Atualizar
- `DELETE /estados/{id}` - Remover

### Cidades
- `GET /cidades` - Listar todas
- `GET /cidades/{id}` - Buscar por ID
- `POST /cidades` - Criar nova
- `PUT /cidades/{id}` - Atualizar
- `DELETE /cidades/{id}` - Remover

---

Projeto educacional do curso Especialista Spring REST da AlgaWorks. Em constante evolução.

