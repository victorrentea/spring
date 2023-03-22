package victor.training.spring.webtoflux;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.webtoflux.table.tables.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static victor.training.spring.webtoflux.table.Tables.AUTHOR;
import static victor.training.spring.webtoflux.table.Tables.BOOK;
import static victor.training.spring.webtoflux.table.tables.AuthorBook.AUTHOR_BOOK;

@Slf4j
@SuppressWarnings("ALL")
@SpringBootApplication
@RestController
public class WebFluxMigrationApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebFluxMigrationApplication.class, args);
  }

  @Autowired
  private DSLContext jooq;
  @Autowired
  private ReactiveDependencies reactiveDependencies;
  @Autowired
  private ClassicDependencies classicDependencies;

  @Value
  public static class BookDto {
    Integer id;
    String title;
  }

  //  public void uploadBooks(@RequestBody Flux<BookDto> incomingJsonFlux) {
  //  }
  // No Tomcat anymore, no HttpServletRequest

  @GetMapping("books")
  //  public Mono<List<BookDto>> books() {
  //  public Flux<BookDto> books() {
  public Flux<BookDto> books() {
    System.out.println("Hey!");
    return Flux.from(jooq.select(BOOK.ID, BOOK.TITLE)
                    .from(BOOK)
            )
            .map(r -> new BookDto(r.value1(), r.value2()))
            ;
  }

  // farewell JDBC, welcome r2dbc to talk to Relational DB
  // any nosql has no problems
  @GetMapping("books/{id}")
  public Mono<BookDto> bookById(@PathVariable Integer id) {
    return Mono.from(jooq.select(BOOK.ID, BOOK.TITLE)
                    .from(BOOK)
                    .where(BOOK.ID.eq(id))
            )
            .map(r -> new BookDto(r.get(BOOK.ID), r.get(BOOK.TITLE)));
  }

  @Value
  public static class CreateBookRequest {
    String title;
    List<Integer> authorIds;
  }

  @PostMapping("books")
  public Mono<Void> createBook(@RequestBody CreateBookRequest dto) {
    return insertBook(dto)
            .delayUntil(bookId -> reactiveDependencies.rabbitSend("Book created: " + bookId))
            .delayUntil(bookId -> Flux.fromIterable(dto.authorIds)
                    .flatMap(authorId -> insertBookAuthor(bookId, authorId)))
            .delayUntil(bookId -> reactiveDependencies.wsdlCall("WebService request: " + bookId))
            .then()
            ;

  }

  private Mono<Integer> insertBook(CreateBookRequest dto) {
    int bookId = new Random().nextInt(100000); // dirty hack :)

    return Mono.from(jooq.insertInto(Book.BOOK)
                    .set(Book.BOOK.ID, bookId)
                    .set(Book.BOOK.TITLE, dto.title))
            .thenReturn(bookId);
  }

  private Mono<Void> insertBookAuthor(Integer bookId, Integer authorId) {
    return Mono.from(jooq.insertInto(AUTHOR_BOOK)
                    .set(AUTHOR_BOOK.AUTHOR_ID, authorId)
                    .set(AUTHOR_BOOK.BOOK_ID, bookId))
            .then();
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
    List<AuthorDto> results = new ArrayList<>();
    Result<Record3<Integer, String, String>> records =
            jooq.select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).from(AUTHOR).fetch();
    for (Record3<Integer, String, String> r : records) {
      Integer authorId = r.value1();
      String bio = fetchBio(authorId);
      results.add(new AuthorDto(r.value1(), r.value2(), r.value3(), bio));
    }
    return results;
  }


  @SneakyThrows
  private String fetchBio(Integer authorId) {
    return new RestTemplate().getForObject("http://localhost:8082/api/teachers/" + authorId + "/bio", String.class);
  }

  @GetMapping("reader/{readerId}/check")
  public boolean checkProfile(@PathVariable Long readerId) {
    ReaderProfile profile = classicDependencies.fetchUserProfile(readerId);
    boolean addressOk = classicDependencies.checkAddress(profile);
    boolean phoneOk = classicDependencies.checkPhone(profile);
    return addressOk && phoneOk;
  }
}
