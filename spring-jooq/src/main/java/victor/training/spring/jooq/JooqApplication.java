package victor.training.spring.jooq;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.jooq.table.tables.Book;

import java.net.URI;
import java.util.List;
import java.util.Random;

import static victor.training.spring.jooq.table.Tables.AUTHOR;
import static victor.training.spring.jooq.table.Tables.BOOK;
import static victor.training.spring.jooq.table.tables.AuthorBook.AUTHOR_BOOK;

@Slf4j
@SuppressWarnings("ALL")
@SpringBootApplication
@RestController
public class JooqApplication {
  public static void main(String[] args) {
    SpringApplication.run(JooqApplication.class, args);
  }

  @Autowired
  private DSLContext dsl;

  @Autowired
  private ReactiveDependencies reactiveDependencies;
  @Autowired
  private ClassicDependencies classicDependencies;

  @Value
  public static class BookDto {
    Integer id;
    String title;
  }

  @GetMapping("books")
  public Flux<BookDto> books() {
    System.out.println("Hey!");
    return Flux.from(dsl.select(BOOK.ID, BOOK.TITLE)
            .from(BOOK))
            .map(r -> new BookDto(r.value1(), r.value2()));
  }
  @GetMapping("books/{id}")
  public Mono<BookDto> booksId(@PathVariable Integer id) {
    return Flux.from(dsl.select(BOOK.ID, BOOK.TITLE)
            .from(BOOK)
            .where(BOOK.ID.eq(id)))
            .map(r -> new BookDto(r.value1(), r.value2()))
            .elementAt(0);
  }

  @Value
  public static class CreateBookRequest {
    String title;
    List<Integer> authorIds;
  }
  @PostMapping("books")
  public Mono<Void> createBook(@RequestBody CreateBookRequest dto) {
    Integer bookId = new Random().nextInt(100000); // dirty hack :)

//    Mono<Integer> insertBookMono = insertBook(dto, bookId);
//    insertBookMono.subscribe();
//    for (Integer authorId : dto.authorIds) {
//      Mono<Integer> insertAuthorMono = insertBookAuthor(bookId, authorId);
//      insertAuthorMono.subscribe();
//    }

    return insertBook(dto, bookId)
            .thenMany(Flux.fromIterable(dto.authorIds))
            .flatMap(authorId -> insertBookAuthor(bookId, authorId))
            .then()
            .doOnNext(v -> {
              System.out.println("Sending rabbit message <- this log is a lie!!");
              reactiveDependencies.rabbitSend("Book created: " + bookId);
            });

  }

  private Mono<Integer> insertBookAuthor(Integer bookId, Integer authorId) {
    Mono<Integer> insertAuthorMono = Mono.from(dsl.insertInto(AUTHOR_BOOK)
            .set(AUTHOR_BOOK.AUTHOR_ID, authorId)
            .set(AUTHOR_BOOK.BOOK_ID, bookId));
    return insertAuthorMono;
  }

  private Mono<Integer> insertBook(CreateBookRequest dto, Integer bookId) {
    return Mono.from(dsl.insertInto(Book.BOOK)
            .set(Book.BOOK.ID, bookId)
            .set(Book.BOOK.TITLE, dto.title));
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

  @GetMapping("reader/{id}/check")
  public boolean checkProfile(Long readerId) {
    ReaderProfile profile = classicDependencies.fetchUserProfile(readerId);
    boolean addressOk = classicDependencies.checkAddress(profile);
    boolean phoneOk = classicDependencies.checkPhone(profile);
    return addressOk && phoneOk;
  }

}
