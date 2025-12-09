package victor.training.spring.web.entity;

public record TrainingId(long id) { // ID type pattern
  @SuppressWarnings("unused")  // Spring uses this to map from a url part into a TrainingId
  public TrainingId(String id) {
    this(Long.parseLong(id));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TrainingId that = (TrainingId) o;
    return id == that.id;
  }

}
