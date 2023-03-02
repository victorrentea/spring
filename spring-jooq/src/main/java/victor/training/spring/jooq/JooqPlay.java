package victor.training.spring.jooq;

import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.spring.jooq.table.tables.Author;
import victor.training.spring.jooq.table.tables.AuthorBook;
import victor.training.spring.jooq.table.tables.Book;

import static victor.training.spring.jooq.table.tables.Author.AUTHOR;
import static victor.training.spring.jooq.table.tables.AuthorBook.AUTHOR_BOOK;
import static victor.training.spring.jooq.table.tables.Book.BOOK;

@SuppressWarnings("ALL")
@Component
public class JooqPlay {
  @Autowired
  private DSLContext dsl;

  @EventListener(ApplicationStartedEvent.class)
  public void onStart() {
    dsl.insertInto(AUTHOR)
            .set(AUTHOR.ID, 4)
            .set(AUTHOR.FIRST_NAME, "Herbert")
            .set(AUTHOR.LAST_NAME, "Schildt")
            .execute();
    dsl.insertInto(BOOK)
            .set(BOOK.ID, 4)
            .set(BOOK.TITLE, "A Beginner's Guide")
            .execute();
    dsl.insertInto(AUTHOR_BOOK)
            .set(AUTHOR_BOOK.AUTHOR_ID, 4)
            .set(AUTHOR_BOOK.BOOK_ID, 4)
            .execute();

    Result<Record3<Integer, String, Integer>> result = dsl
            .select(AUTHOR.ID, AUTHOR.LAST_NAME, DSL.count())
            .from(AUTHOR)
            .join(AUTHOR_BOOK)
            .on(AUTHOR.ID.equal(AUTHOR_BOOK.AUTHOR_ID))
            .join(BOOK)
            .on(AUTHOR_BOOK.BOOK_ID.equal(BOOK.ID))
            .groupBy(AUTHOR.LAST_NAME)
            .fetch();
    System.out.println("result: " + result.formatJSON());
  }
}
