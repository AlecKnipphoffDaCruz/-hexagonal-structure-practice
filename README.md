# Hexagonal Architecture — Spring Boot Reference Implementation

A hands-on reference project demonstrating **Hexagonal Architecture** (Ports & Adapters) using Spring Boot 4 and Java 25. Built as a clean, minimal example of how to structure a domain-centric application where business logic is completely isolated from infrastructure concerns.

---

## What is Hexagonal Architecture?

Hexagonal Architecture (also known as **Ports & Adapters**) places the domain at the center of the application. All external concerns — HTTP, databases, messaging — are pushed to the edges and communicate with the domain only through well-defined interfaces called **Ports**.

```
┌─────────────────────────────────────────────────────────┐
│                      ADAPTERS IN                        │
│               (UserController — REST)                   │
│                          │                              │
│              ┌───────────▼────────────┐                 │
│              │       DOMAIN           │                 │
│              │                        │                 │
│              │  User (record)         │                 │
│              │  UserService           │                 │
│              │  JpaPersistencePort ───┼──►  ADAPTER OUT │
│              │  UserMapper            │   (FakePostgres)│
│              └────────────────────────┘                 │
└─────────────────────────────────────────────────────────┘
```

The domain never imports HTTP, JPA, or any framework-specific code. **It only knows about itself.**

---

## Project Structure

```
src/main/java/com/example/hexagonalstruct/
└── user/
    │
    ├── User.java                        # Domain model — immutable Java Record
    ├── UserService.java                 # Business logic — depends on port, not adapter
    ├── UserMapper.java                  # Mapping between layers (DTO ↔ Domain ↔ Entity)
    ├── JpaPersistencePort.java          # Output port — contract for persistence
    │
    ├── zin/                             # INPUT adapters — things that drive the domain
    │   └── UserController.java          # REST controller
    │
    ├── zout/                            # OUTPUT adapters — things the domain needs
    │   ├── FakePostgresAdpter.java      # In-memory persistence (implements the port)
    │   └── UserEntity.java              # JPA-ready entity (DB representation)
    │
    └── zdto/                            # Data Transfer Objects (HTTP boundary only)
        ├── CreateUserDto.java
        └── ResponseUserDto.java
```

### The `zin / zout / zdto` Convention

| Prefix | Meaning | What lives here |
|--------|---------|-----------------|
| `zin/` | Input adapter | REST controllers, message listeners — entry points into the domain |
| `zout/` | Output adapter | Persistence adapters, external service clients — exit points from the domain |
| `zdto/` | Data Transfer Objects | Request/response records used at the HTTP boundary only — never inside services |

Business logic (services, ports) lives at the **package root** — no prefix.

---

## The Two User Representations

This is the most important concept in the codebase.

| Class | Package | What it is | Framework imports |
|-------|---------|------------|-------------------|
| `User.java` | `user/` | Immutable domain record — the business truth | None |
| `UserEntity.java` | `user/zout/` | DB mapping class — JPA annotations | Hibernate / JPA |

`UserService` works exclusively with `User`. `UserEntity` never leaves `zout/persistence/`. `UserMapper` is the only class that converts between them.

```java
// Correct conversion chain — always passes through the domain
CreateUserDto → User (domain) → UserEntity (persistence)
UserEntity    → User (domain) → ResponseUserDto
```

---

## Request Flow

A `POST /user/create` request travels through the following layers:

```
1. UserController      receives CreateUserDto (HTTP)
       │  calls userMapper.dtoToDomain(dto)
       ▼
2. UserService         receives User (domain record)
       │  calls persistencePort.save(user)
       ▼
3. JpaPersistencePort  contract invoked (interface — domain layer)
       │
       ▼
4. FakePostgresAdpter  implements the port
       │  maps User → UserEntity internally
       │  stores in-memory, maps back to User
       ▼
5. UserService         returns User to controller
       │
       ▼
6. UserController      maps User → ResponseUserDto
       │  returns ResponseEntity<ResponseUserDto>
       ▼
7. Client              receives JSON response
```

---

## Key Architectural Rules

**1. The service injects the port, never the adapter**
```java
// Correct
private final JpaPersistencePort persistencePort;

// Wrong — breaks the isolation
private final FakePostgresAdpter repo;
```

**2. Services speak domain language only**
```java
// Correct
public User createUser(User user) { ... }

// Wrong — DTO leaking into the domain
public ResponseUserDto createUser(CreateUserDto dto) { ... }
```

**3. DTOs never enter a service**
The controller handles all `DTO ↔ Domain` translation before and after calling the service.

**4. UserEntity never leaves zout/**
If a class outside `zout/` needs a user, it receives a `User` record — never a `UserEntity`.

**5. The port is replaceable without touching the domain**
Swapping `FakePostgresAdpter` for a real PostgreSQL adapter requires zero changes to `UserService`.

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Runtime | Java 25, Spring Boot 4.0.6 |
| REST | Spring MVC |
| Persistence | In-memory `HashMap` (fake adapter — no DB required) |
| Boilerplate reduction | Lombok |
| Build | Maven |

---

## Running Locally

```bash
./mvnw spring-boot:run
```

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/user/create` | Create a new user |
| `GET` | `/user/get/{id}` | Retrieve a user by ID |

### Example Request

```bash
curl -X POST http://localhost:8080/user/create \
  -H "Content-Type: application/json" \
  -d '{"name": "Maria Silva", "age": 28, "description": "Backend developer"}'
```

```json
{
  "id": 1,
  "name": "Maria Silva",
  "age": 28,
  "description": "Backend developer"
}
```

---

## Extending This Project

The patterns here scale directly to production. To add a new domain (e.g. `product/`):

1. Create `product/Product.java` — the domain record
2. Create `product/ProductPersistencePort.java` — the output port interface
3. Create `product/ProductService.java` — business logic, injecting the port
4. Create `product/zout/ProductAdapter.java` — implements the port
5. Create `product/zin/ProductController.java` — maps HTTP → domain → HTTP
6. Create `product/zdto/` — request and response records

The domain never knows which adapter is wired — Spring injects it at runtime.
