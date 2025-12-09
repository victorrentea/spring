package victor.training.spring.varie.advancedvalidation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerDto {
    interface ActivationUC {
    }

    @Email
    public String email;

    @NotBlank(groups = ActivationUC.class)
    public String name;

    @ValidPostalCode(groups = ActivationUC.class)
    public String postalCode;
}
