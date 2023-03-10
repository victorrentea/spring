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
  public List<BookDto> books() {
    System.out.println("Hey!");
    return dsl.select(BOOK.ID, BOOK.TITLE).from(BOOK)
            .fetch()
            .map(r -> new BookDto(r.value1(), r.value2()));
  }

  @GetMapping("books/{id}")
  public BookDto bookById(@PathVariable Integer id) {
    return dsl.select(BOOK.ID, BOOK.TITLE)
            .from(BOOK)
            .where(BOOK.ID.eq(id))
            .fetchSingle()
            .map(r -> new BookDto(r.get(BOOK.ID), r.get(BOOK.TITLE)));
  }

  @Value
  public static class CreateBookRequest {
    String title;
    List<Integer> authorIds;
  }

  @PostMapping("books")
  public void createBook(@RequestBody CreateBookRequest dto) {
    int bookId = insertBook(dto);

    classicDependencies.rabbitSend("Book created: " + bookId);
    for (Integer authorId : dto.authorIds) {
      insertBookAuthor(bookId, authorId);
    }
    classicDependencies.wsdlCall("WebService request: " + bookId);

    classicDependencies.rabbitSend("Book created: " + bookId);
  }

  private int insertBook(CreateBookRequest dto) {
    int bookId = new Random().nextInt(100000); // dirty hack :)

    dsl.insertInto(Book.BOOK)
            .set(Book.BOOK.ID, bookId)
            .set(Book.BOOK.TITLE, dto.title)
            .execute();
    return bookId;
  }

  private void insertBookAuthor(Integer bookId, Integer authorId) {
    dsl.insertInto(AUTHOR_BOOK)
            .set(AUTHOR_BOOK.AUTHOR_ID, authorId)
            .set(AUTHOR_BOOK.BOOK_ID, bookId)
            .execute();
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
            dsl.select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).from(AUTHOR).fetch();
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
