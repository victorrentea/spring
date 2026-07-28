# Project Conventions for AI Agents

## Dependency Injection

Prefer **Lombok-based constructor injection** using `@RequiredArgsConstructor` and `private final` fields.

- Declare dependencies as `private final` fields
- Annotate the class with `@RequiredArgsConstructor` (Lombok)
- **Never** use `@Autowired` on a single constructor — Spring injects it automatically

```java
// ✅ Preferred
@Service
@RequiredArgsConstructor
public class MyService {
    private final MyRepository myRepository;
}

// ❌ Avoid
@Service
public class MyService {
    private final MyRepository myRepository;

    @Autowired
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```
