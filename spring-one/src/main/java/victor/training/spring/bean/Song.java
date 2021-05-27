package victor.training.spring.bean;

public class Song {
   private final String title;
   private final Singer singer;

   public Song(String title, Singer singer) {
      this.title = title;
      this.singer = singer;
   }
}
