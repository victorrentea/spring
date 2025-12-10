package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class NPlusOne {
  private final MyEntityRepo repo;
  private final ChildRepo childRepo;

  public void insert() {
    repo.save(new MyEntity().setName("burlac cu 3 pisici - Trofim"));
    for (int i = 0; i < 2000; i++) {
      repo.save(new MyEntity()
              .addChild(new Child().setName("Child " + i + "-1"))
              .setName("Entity " + i));
    }
  }
  @Transactional(readOnly = true)
  void export() {
    List<MyEntity> parents = repo.findAll();
    List<Long> parentIds = parents.stream().map(MyEntity::getId).toList();
    Map<Long, List<Child>> childrenByParentId = childRepo.findByParentIdIn(parentIds).stream()
            .collect(Collectors.groupingBy(child -> child.getParent().getId()));

    for (MyEntity entity : parents) {
      log.info("Entity in CSV: " + entity.getName() + ";" + childrenByParentId.get(entity.getId()));
    }
  }
}
