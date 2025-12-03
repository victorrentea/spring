package victor.training.spring.ai;

import org.springframework.data.jpa.repository.JpaRepository;

interface DogRepo extends JpaRepository<Dog, Integer> {}
