# Agent instructions

## API docs travel with the DTOs

OpenAPI docs live on `*ApiDocs` interfaces next to their controllers (e.g. `TrainingApiDocs`),
not on the controllers. Other teams load their request/response examples straight into WireMock,
so a stale example breaks their stubs silently.

Whenever you change a DTO used by a documented endpoint (add/rename/remove a field, change a type,
a format like `@JsonFormat`, a validation like `@Size`, or an error message), in the same change:
- update the field list in the `@Operation` description and every `@ExampleObject` body
- keep request and response examples paired **by the same name**: one pair = one WireMock stub
- prove it: start the app and replay each example against it — status and body must match the docs
