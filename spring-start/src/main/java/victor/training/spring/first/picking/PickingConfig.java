package victor.training.spring.first.picking;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DronePicking.class,
    RoboArmPicking.class,
    ManualLaborPicking.class,
    PickerService.class})
public class PickingConfig {
}
