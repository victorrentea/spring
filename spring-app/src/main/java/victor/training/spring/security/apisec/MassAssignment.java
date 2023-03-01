package victor.training.spring.security.apisec;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@RestController
@RequiredArgsConstructor
public class MassAssignment {
  private final PlayerRepo playerRepo;
  private final PlayerMapper mapper;

  @GetMapping("player/details")
  public PlayerDto getPlayerDetails() {
    Player entity = playerRepo.findById(getCurrentPlayerId()).orElseThrow();
    return mapper.toDto(entity);
  }

  //<editor-fold desc="SOLUTION">
  //  @Data
//  public static class UpdatePlayerDetailsCommand {
//    private String fullName;
//    private String country;
//  }
  //</editor-fold>

  @PutMapping("player/details")
  @Transactional
  public void updatePlayerDetails(@RequestBody PlayerDto dto) {
    Player entity = playerRepo.findById(getCurrentPlayerId()).orElseThrow();
    mapper.update(entity, dto);
  }
  private static long getCurrentPlayerId() {
    return SecurityContextHolder.getContext().getAuthentication().getName().equals("user") ?
            1L : 3L;
  }

  //<editor-fold desc="initial data">
  @EventListener(ApplicationStartedEvent.class)
  public void insertInitialPlayers() {
    playerRepo.save(new Player().setId(1L).setCountry("RO").setUsername("ipopescu").setCash(100).setFullName("Ion Popescu"));
    playerRepo.save(new Player().setId(2L).setCountry("NL").setUsername("snoah").setCash(250).setFullName("Saar Noah"));
    playerRepo.save(new Player().setId(3L).setCountry("BE").setUsername("jdoe").setCash(150).setFullName("John Doe"));
  }
  //</editor-fold>
}
@Data
class PlayerDto {
  private Long id;
  private String username;
  private String fullName;
  private String country;
  private Integer cash;
}

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE)
interface PlayerMapper {
  void update(@MappingTarget Player entity, PlayerDto dto); // FIXME don't copy all fields
  PlayerDto toDto(Player entity);
}



@Entity
@Data
class Player {
  @Id
  private Long id;
  private String username;
  private String fullName;
  private String country;
  private Integer cash;

}

interface PlayerRepo extends JpaRepository<Player, Long> {
}