# spring-sequence-diagram

Sequence diagrams of what your Spring Boot app **really** ran: who called whom through a proxy,
where each transaction **begins, is suspended, resumes and ends**, and every statement sent to the
database, with its parameters.

One coloured frame per physical transaction;
`REQUIRES_NEW` / `NOT_SUPPORTED` close the caller's frame and reopen it as *(resumed)*; an exception
crossing a joined `@Transactional` proxy shows *☠ marked rollback-only*.

## Add it — no code change

```xml
<dependency>
    <groupId>victor.training.spring</groupId>
    <artifactId>spring-sequence-diagram</artifactId>
    <version>1.0</version>
    <scope>test</scope> <!-- or compile, to also draw HTTP requests of the running app -->
</dependency>
```

It is a Spring Boot auto-configuration: nothing to annotate in production code, no aspect to write.
The beans it follows are those under the package of your `@SpringBootApplication`.

## Get diagrams

| from | how | where |
|---|---|---|
| a test | `@SequenceDiagram` on a `@SpringBootTest` class or method | `target/sequence/{Test}.{method}.svg` |
| every test, no annotation | `junit.jupiter.extensions.autodetection.enabled=true` in `src/test/resources/junit-platform.properties` | same |
| an HTTP request | just send it (Spring MVC apps) | `target/sequence/GET-orders.svg` + live at **http://localhost:8080/sequence** |
| a call into a bean from nowhere (startup work, a scheduler) | nothing | `target/sequence/OrderJob.run().svg` |

`target/sequence/index.html` shows them all, newest first. Each `.svg` has its `.puml` next to it.

## Any database

SQL is captured by [datasource-proxy](https://github.com/jdbc-observations/datasource-proxy) wrapped
around every `DataSource` bean — between your code and the pool — so it works with any JDBC driver
(the module's own tests run the same scenarios on H2 and HSQLDB). JPA/Hibernate, `JdbcTemplate`, jOOQ,
MyBatis: they all end up there. Two `DataSource` beans draw two database lifelines.

Transactions are seen by a `TransactionExecutionListener` attached to every
`AbstractPlatformTransactionManager` bean (JDBC, JPA, JTA, or one you declared yourself).

## Properties

| property | default | |
|---|---|---|
| `sequence.enabled` | `true` | off switch |
| `sequence.base-packages` | your `@SpringBootApplication`'s package | whose beans are participants |
| `sequence.output-dir` | `target/sequence` | |
| `sequence.show-connections` | `true` | draw `getConnection()` / `close()` on the pool |
| `sequence.max-label-length` | `110` | longer SQL is cut with … |
| `sequence.keep-in-memory` | `30` | diagrams remembered by `/sequence` |
| `sequence.http.enabled` | `true` | a diagram per request + the `/sequence` page |
| `sequence.http.excluded-paths` | `/sequence, /h2-console, /actuator, /favicon.ico, /error` | |

## Requirements and limits

- Spring Boot 3.2+ (Spring 6.1 `TransactionExecutionListener`), Java 17+. Spring MVC for the HTTP part; WebFlux and R2DBC are not covered.
- Only calls through a Spring proxy are drawn — exactly the calls `@Transactional` can see. `this.method()` draws nothing.
- Work on another thread is drawn in a 🧵 frame of its own, outside the caller's transactions; the diagram waits for it to end.
  It is followed through every `Executor` **bean** (`@Async`, `CompletableFuture.supplyAsync(task, executor)`, `submit()`),
  and guessed on the common pool (`CompletableFuture.runAsync()` without an executor, parallel streams) only if exactly
  one diagram is open when it starts. A `new Thread()` or an executor that is not a bean is not followed.
- *rollback-only* is detected for `@Transactional`, not for `TransactionTemplate#setRollbackOnly()`.
- Spans are recorded with its own OpenTelemetry `SdkTracerProvider`, never exported: it coexists with an
  OpenTelemetry agent, but don't run both on the same JVM if you compare them — the agent also instruments
  the connection proxy, and reports each `COMMIT` twice.
