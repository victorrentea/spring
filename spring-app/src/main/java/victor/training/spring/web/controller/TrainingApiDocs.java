package victor.training.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import victor.training.spring.web.controller.dto.TrainingDto;

/**
 * OpenAPI documentation of {@link TrainingController}, kept here so the controller stays readable.
 * springdoc reads these annotations from the interface method the controller overrides.
 */
public interface TrainingApiDocs {

  @Operation(
      summary = "Create a training",
      description = """
          Schedules a new training, taught by an existing teacher.

          Fields read from the body:
          - `name` (required, 3-50 chars) — must be unique among all trainings
          - `teacherId` (required) — id of an existing teacher, see `GET /api/teachers`
          - `language` — one of `JAVA`, `PHP`, `KOTLIN`
          - `startDate` — format **dd-MM-yyyy** (not ISO!)
          - `description` — may contain HTML

          Ignored on create: `id` (assigned by the server), `version`, `level`, `teacherName`, `teacherBio`.

          The response has no body: to get the new id, search by name via `GET /api/trainings/search?name=...`.

          Mocking (WireMock, Microcks, Prism): each request example is paired with the response example
          **of the same name** (`created`, `nameTooShort`, `duplicateName`), so every pair imports as one stub.
          The bodies are real: replayed against the seed data, they produce exactly these responses.""",
      requestBody = @RequestBody(
          required = true,
          content = @Content(
              mediaType = "application/json",
              examples = {
                  @ExampleObject(name = "created", summary = "Valid training → 200", value = """
                      {
                        "name": "Spring Security",
                        "teacherId": 1,
                        "language": "JAVA",
                        "startDate": "15-12-2026",
                        "description": "<p>OAuth2, JWT and method-level <b>@PreAuthorize</b></p>"
                      }"""),
                  @ExampleObject(name = "nameTooShort", summary = "Name under 3 chars → 400", value = """
                      {
                        "name": "Sp",
                        "teacherId": 1,
                        "language": "JAVA",
                        "startDate": "15-12-2026",
                        "description": "<p>Too short a name</p>"
                      }"""),
                  @ExampleObject(name = "duplicateName", summary = "Name already taken → 500", value = """
                      {
                        "name": "Spring Framework",
                        "teacherId": 1,
                        "language": "JAVA",
                        "startDate": "15-12-2026",
                        "description": "<p>Same name as an existing training</p>"
                      }""")
              })),
      responses = {
          @ApiResponse(responseCode = "200", description = "Training created (empty body)",
              content = @Content(mediaType = "text/plain", examples = @ExampleObject(name = "created", value = ""))),
          @ApiResponse(responseCode = "400", description = "Validation failed, e.g. `name` not within 3-50 chars. " +
                                                           "Body: the error message(s) as plain text, translated per `Accept-Language` (en, ro, de)",
              content = @Content(mediaType = "text/plain", examples = @ExampleObject(name = "nameTooShort", value = "Customer length is incorrect"))),
          @ApiResponse(responseCode = "500", description = "A training with the same `name` already exists, or `teacherId` is unknown. " +
                                                           "Body: the error message as plain text",
              content = @Content(mediaType = "text/plain", examples = @ExampleObject(name = "duplicateName", value = "Another training with that name already exists")))
      })
  void create(TrainingDto dto);
}
