package victor.training.spring.web.spring.feed;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import victor.training.spring.web.feed.FeedProcessor;
import victor.training.spring.web.feed.FileRepoInMemForTests;

import java.io.File;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
// doar pt debugging
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD) // nu comiti asta pe Jenkins niciodata. Iti pierde foarte mult timp.
public class FeedProcessorWithFakeTest {
   @Autowired
   private FeedProcessor feedProcessor;
   @Autowired
   private FileRepoInMemForTests fileRepoFake;

   @Autowired
   private CacheManager cacheManager;

   @Before
   public void initialize() {
      cacheManager.getCache("primes").clear(); // fain
      fileRepoFake.clearFiles();
   }

   @Test
   public void oneFileWithOneLine() {
      fileRepoFake.addTestFile("one.txt","one");
      assertThat(feedProcessor.countPendingLines()).isEqualTo(1);
   }

   @Test
   public void oneFileWith2Lines() {
      fileRepoFake.addTestFile("two.txt","one", "two");
      assertThat(feedProcessor.countPendingLines()).isEqualTo(2);
   }

   @Test
   public void twoFilesWith3Lines() {
      fileRepoFake.addTestFile("one.txt","one");
      fileRepoFake.addTestFile("two.txt","one", "two");
      assertThat(feedProcessor.countPendingLines()).isEqualTo(3);
   }
   @Test
   public void twoFilesWith3LinesAnd1CommentedLine() {
      fileRepoFake.addTestFile("one.txt","one");
      fileRepoFake.addTestFile("two.txt","one", "two", "#comment");
      assertThat(feedProcessor.countPendingLines()).isEqualTo(3);
   }

   // TODO IMAGINE EXTRA DEPENDENCY
}
