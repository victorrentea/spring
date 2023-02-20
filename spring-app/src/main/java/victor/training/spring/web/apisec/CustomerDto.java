package victor.training.spring.web.apisec;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CustomerDto {
    interface ActivationUC {}

    @Email
    public String email;

    @NotBlank(groups = ActivationUC.class)
    public String name;

    public String postalCode;
}
