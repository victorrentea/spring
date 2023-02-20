package victor.training.spring.web.apisec;

import lombok.*;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.persistence.CascadeType.PERSIST;

@RequiredArgsConstructor
@RestController
public class DataExposure {
  private final BlogArticleRepo repo;

  @Value
  public static class BlogListDto {
    long id;
    String title;
    public BlogListDto(BlogArticle entity) {
      id = entity.getId();
      title = entity.getTitle();
    }
  }
  @GetMapping("api/articles")
  public List<BlogListDto> getAllArticles() {
    return repo.findAll().stream().map(BlogListDto::new).collect(toList());
  }

  @GetMapping("api/articles/{id}")
  public BlogArticle article(@PathVariable Long id) {
    return repo.findById(id).orElseThrow();
  }

  //<editor-fold desc="Initial Data">
  @Transactional
  @EventListener(ApplicationStartedEvent.class)
  public void insertData() {
    for (int i = 0; i < 4; i++) {
      repo.save(new BlogArticle()
              .setTitle("All about Excessive Data Exposure"+i)
              .setText("Checkout the JSON response you got to this page"+i)
              .setDate(LocalDate.now().minusDays(2))
              .setComments(List.of(
                      new BlogArticleComment("Lame!",new BlogUser("King Julian", "kj@madagascar.com")),
                      new BlogArticleComment("Wow",new BlogUser("Julie", "julie.private@gmail.com"))
              ))
      );
    }
  }
  //</editor-fold>
}

@Data
@Entity
class BlogArticle {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private String text;
  private LocalDate date;
  @OneToMany(cascade = PERSIST)
  private List<BlogArticleComment> comments = new ArrayList<>();
}

@Entity
@Data
class BlogArticleComment {
  @Id
  @GeneratedValue
  private Long id;
  private String text;
  @ManyToOne(cascade = PERSIST)
  private BlogUser author;
  protected BlogArticleComment() {} // for Hibernate only

  public BlogArticleComment(String text, BlogUser author) {
    this.text = text;
    this.author = author;
  }
}

@Data
@Entity
class BlogUser {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String email;
  protected BlogUser() {} // for Hibernate only

  public BlogUser(String name, String email) {
    this.name = name;
    this.email = email;
  }
}

interface BlogArticleRepo extends JpaRepository<BlogArticle, Long> {
}