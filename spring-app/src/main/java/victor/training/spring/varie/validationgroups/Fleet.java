package victor.training.spring.varie.validationgroups;

import jakarta.validation.Valid;

import java.util.List;

/**
 * Container pentru a demonstra cascadarea (@Valid) peste o colectie POLIMORFICA:
 * fiecare element isi aplica propria redefinire de Default, in functie de tipul lui real.
 */
public record Fleet(@Valid List<Vehicle> vehicles) {
}
