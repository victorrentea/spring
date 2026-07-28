# Project Conventions for AI Agents

## Dependency Injection

Prefer **Lombok-based constructor injection** using `@RequiredArgsConstructor` and `private final` fields.

- Declare dependencies as `private final` fields
- Annotate the class with `@RequiredArgsConstructor` (Lombok)
- **Never** use `@Autowired` on a single constructor — Spring injects it automatically

## Testing

**Tests must be decoupled from implementation.** Test observable behavior, not internal structure.

- Assert on outputs and side effects, not on which beans were injected or how internals are wired
- Never expose methods or fields solely for the purpose of testing them (e.g. `processors()` to inspect an injected list)
- If the only way to test something is to peek at internals, the test is wrong — rewrite it to verify behavior through the public API
- Order of execution, branching logic, edge cases — all should be proven via **data in, data out**, not by inspecting collaborators

## Workflow

**Always run the tests before committing.** Never commit code that hasn't been verified to compile and pass tests.
