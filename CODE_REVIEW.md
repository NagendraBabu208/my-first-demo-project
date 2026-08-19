# Code Review Report — Book Management Application

Review date: 2026-08-19
Scope: Full repository, standards/coding-conventions pass (build config, layering, naming, REST conventions, validation).

---

## Build-Breaking Issues

### 1. Bogus dependency in `pom.xml`
**File:** `pom.xml:67-71`

```xml
<dependency>
  <groupId>com.micro</groupId>
  <artifactId>Book-Catalog-App</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

**Why it shouldn't be there:** This artifact does not exist in any Maven repository. Maven will fail dependency resolution immediately, so the project cannot build or run at all.

**Fix:** Remove this `<dependency>` block entirely.

---

### 2. Incorrect Spring Boot starter artifact IDs
**File:** `pom.xml:39, 55, 60`

- `spring-boot-starter-webmvc` (line 39)
- `spring-boot-starter-data-jpa-test` (line 55)
- `spring-boot-starter-webmvc-test` (line 60)

**Why it shouldn't be there:** None of these are real Spring Boot artifact IDs, so Maven cannot resolve them and the build fails before compilation even starts.

**Fix:**
- Replace `spring-boot-starter-webmvc` with `spring-boot-starter-web`.
- Replace both `-test` variants with a single `spring-boot-starter-test` (scope `test`) — it already provides MockMvc, JPA test support, JUnit, and Mockito.

---

## Security Issues

### 3. Plaintext database credentials
**File:** `src/main/resources/application.properties:3-4`

```properties
spring.datasource.username=root
spring.datasource.password=root
```

**Why it shouldn't be there:** Hardcoding real credentials in a file committed to source control exposes them to anyone with repo access and leaks them into git history permanently, even if removed later.

**Fix:** Externalize credentials via environment variables:
```properties
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```
Set the values locally or in a gitignored `application-local.properties`, never committed.

---

## Validation Gap

### 4. `@Valid` on `BookDTO` currently validates nothing
**File:** `src/main/java/com/shristi/tech/model/BookDTO.java`

**Why it shouldn't be there:** The controller uses `@Valid @RequestBody BookDTO`, but `BookDTO` has no Bean Validation annotations (`@NotEmpty`, etc.) — those constraints still only exist on the `Book` entity, which requests no longer touch after the DTO refactor. As written, `@Valid` is a no-op: empty `title`/`author` values will pass straight through.

**Fix:** Copy the relevant constraints (`@NotEmpty` on `title` and `author`, and any others needed) from `Book` onto `BookDTO`'s fields.

---

## Consistency / Design Issues

### 5. `createBook` breaks the DTO abstraction boundary
**File:** `src/main/java/com/shristi/tech/service/IBookService.java:10`

**Why it shouldn't be there:** Every other method in `IBookService` returns `BookDTO`, but `createBook` returns the raw `Book` entity. This leaks persistence internals into a layer meant to only expose DTOs, and forces the controller to map the entity manually instead of relying on the service.

**Fix:** Change `createBook` to return `BookDTO`, mapping inside the service via `BookMapper`, consistent with the other methods.

---

### 6. Inconsistent "not found" behavior across list endpoints
**File:** `src/main/java/com/shristi/tech/service/BookServiceImpl.java:40-51, 63-79, 81-98`

**Why it shouldn't be there:** `getAllBooks()` silently returns an empty list when there are no books, but `getByAuthor()`/`getByTitle()` throw `BookNotFoundException` (404) for the same underlying condition — no matching results. This is inconsistent behavior for structurally identical "list" endpoints and forces API consumers to handle the same case two different ways.

**Fix:** Pick one behavior and apply it uniformly — recommended: return `200 OK` with an empty list for all three, reserving `404` for single-resource lookups like `getBookById`.

---

### 7. Non-RESTful create endpoint path
**File:** `src/main/java/com/shristi/tech/controller/BookController.java:42`

```java
@PostMapping("/create-book")
```

**Why it shouldn't be there:** Every other endpoint on this resource uses the collection path `/books` (`GET /books`, `GET /books/{id}`, `PATCH /books/{id}`, `DELETE /books/{id}`). REST convention is `POST /books` to create a new resource in the collection; `/create-book` is a verb-based, non-standard path that breaks that pattern.

**Fix:** Change the mapping to `@PostMapping("/books")`.

---

### 8. DELETE returns 200 + body instead of 204
**File:** `src/main/java/com/shristi/tech/controller/BookController.java:78-83`

**Why it shouldn't be there:** Convention for a successful DELETE with no meaningful representation to return is `204 No Content`. Returning `200 OK` with the deleted ID in the body is non-standard and can confuse consumers expecting the convention.

**Fix:** Return `ResponseEntity.noContent().build()`, or explicitly document the deviation if the ID is intentionally useful to callers.

---

## Minor Style / Naming Issues

### 9. Misspelled field name `booMapper`
**File:** `src/main/java/com/shristi/tech/controller/BookController.java:33`

**Why it shouldn't be there:** `booMapper` is missing a letter (should be `bookMapper`). Misspelled identifiers reduce readability and break searchability (grepping for `bookMapper` won't find this usage).

**Fix:** Rename the field (and its usages) to `bookMapper`.

---

### 10. Legacy `java.util.Date` used for dates
**File:** `src/main/java/com/shristi/tech/entity/Book.java:26`

**Why it shouldn't be there:** `java.util.Date` is mutable and has known timezone-handling pitfalls. Using it in an otherwise modern codebase (Java 17, Spring Boot) invites subtle timezone/serialization bugs.

**Fix:** Replace with `java.time.LocalDate` (or `LocalDateTime` if time-of-day matters), and update `BookDTO`/`BookMapper` accordingly.

---

### 11. Weak/unstructured global error responses
**File:** `src/main/java/com/shristi/tech/exception/GlobalExceptionHandler.java`

**Why it shouldn't be there:** `@ControllerAdvice` combined with `ResponseEntity<String>` returns raw string error bodies. A more standard API error contract returns a structured error object (e.g. `status`, `message`, `timestamp`, `path`) rather than a bare string, and `@RestControllerAdvice` is the more idiomatic annotation choice when every handler returns a body directly.

**Fix:** Switch to `@RestControllerAdvice` and introduce a small `ErrorResponse` DTO (status, message, timestamp) returned by both handlers.

---

## Summary Table

| # | Issue | Severity | File |
|---|-------|----------|------|
| 1 | Bogus Maven dependency | Blocker | pom.xml |
| 2 | Wrong starter artifact IDs | Blocker | pom.xml |
| 3 | Plaintext DB credentials | Security | application.properties |
| 4 | `@Valid` validates nothing on BookDTO | High | BookDTO.java |
| 5 | `createBook` returns entity not DTO | Medium | IBookService.java |
| 6 | Inconsistent empty-result semantics | Medium | BookServiceImpl.java |
| 7 | Non-RESTful `/create-book` path | Medium | BookController.java |
| 8 | DELETE returns 200 instead of 204 | Low | BookController.java |
| 9 | Misspelled `booMapper` field | Low | BookController.java |
| 10 | Legacy `java.util.Date` | Low | Book.java |
| 11 | Unstructured error responses | Low | GlobalExceptionHandler.java |
