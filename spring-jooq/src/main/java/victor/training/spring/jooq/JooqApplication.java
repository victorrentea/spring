package victor.training.spring.jooq;

import lombok.SneakyThrows;
import lombok.Value;
import org.jooq.DSLContext;
import org.jooq.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.spring.jooq.table.tables.Author;
import victor.training.spring.jooq.table.tables.Book;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static victor.training.spring.jooq.table.Tables.AUTHOR;
import static victor.training.spring.jooq.table.Tables.BOOK;
import static victor.training.spring.jooq.table.tables.AuthorBook.AUTHOR_BOOK;

@SuppressWarnings("ALL")
@SpringBootApplication
@RestController
public class JooqApplication {
  public static void main(String[] args) {
    SpringApplication.run(JooqApplication.class, args);
  }

  @Autowired
  private DSLContext dsl;


  @Value
  public static class BookDto {
    Integer id;
    String title;
  }

  @GetMapping("books")
  public List<BookDto> books() {
    return dsl.select(BOOK.ID, BOOK.TITLE)
            .from(BOOK)
            .fetch()
            .map(r -> new BookDto(r.value1(), r.value2()));
  }

  @Value
  public static class CreateBookRequest {
    String title;
    List<Integer> authorIds;
  }
  @PostMapping("books")
  public void createBook(@RequestBody CreateBookRequest dto) {
    Integer id = new Random().nextInt(100000); // dirty hack :)
    dsl.insertInto(Book.BOOK)
            .set(Book.BOOK.ID,id )
            .set(Book.BOOK.TITLE, dto.title)
            .execute();
    for (Integer authorId : dto.authorIds) {
      dsl.insertInto(AUTHOR_BOOK)
              .set(AUTHOR_BOOK.AUTHOR_ID, authorId)
              .set(AUTHOR_BOOK.BOOK_ID, id)
              .execute();
    }
  }

  @Value
  public static class AuthorDto {
    Integer id;
    String firstName;
    String lastName;
    String bio;
  }
  @GetMapping("authors")
  public List<AuthorDto> authors() {
    return dsl.select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
            .from(AUTHOR)
            .fetch()
            .map(r -> new AuthorDto(r.value1(), r.value2(), r.value3(), fetchBio(r.value1())));
  }

  @SneakyThrows
  private String fetchBio(Integer authorId) {
    ResponseEntity<String> response = new RestTemplate().exchange(new RequestEntity<>(
            HttpMethod.GET,
            new URI("http://localhost:8082/api/teachers/" + authorId + "/bio")), String.class);
    return response.getBody();
  }



}
