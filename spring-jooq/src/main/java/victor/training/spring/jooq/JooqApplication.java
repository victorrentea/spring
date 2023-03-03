package victor.training.spring.jooq;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import victor.training.spring.jooq.table.tables.Book;

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
            .then(reactiveDependencies.rabbitSend("Book created: " + bookId));

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
  public Flux<AuthorDto> authors() {
    //BAD
//    for(id : listId) {
//      bio = network.fetchBio(id)
//    }
    GOOD:
//    List<bio> = network.bulkFetchBio(listId)

    return Flux.from(dsl.select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
            .from(AUTHOR))
            .flatMap(r -> fetchBio(r.value1())
                    .onErrorReturn("N/A")
                    .map(bio-> new AuthorDto(r.value1(), r.value2(), r.value3(),bio)));
  }




  @SneakyThrows
  private Mono<String> fetchBio(Integer authorId) {
//    ResponseEntity<String> response = new RestTemplate().exchange(new RequestEntity<>(
//            HttpMethod.GET,
//            new URI("http://localhost:8082/api/teachers/" + authorId + "/bio")), String.class);
//    return response.getBody();
    return WebClient.create().get().uri("http://localhost:8082/api/teachers/" + authorId + "/bio")
            .retrieve()
            .bodyToMono(String.class);
  }

  @GetMapping("reader/{id}/check")
  public boolean checkProfile(Long readerId) {
    ReaderProfile profile = classicDependencies.fetchUserProfile(readerId);
    boolean addressOk = classicDependencies.checkAddress(profile);
    boolean phoneOk = classicDependencies.checkPhone(profile);
    return addressOk && phoneOk;
  }

}
