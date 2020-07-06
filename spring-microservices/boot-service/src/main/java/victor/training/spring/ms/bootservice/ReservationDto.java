package victor.training.spring.ms.bootservice;

import lombok.Data;

@Data
public class ReservationDto {
   private Long id;
   private String name;
   public ReservationDto(Reservation entity) {
      id = entity.getId();
      name = entity.getName();
   }
}
