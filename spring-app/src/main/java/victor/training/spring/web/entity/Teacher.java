package victor.training.spring.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Teacher {
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private ContractType contractType;

	private String name;

	@BatchSize(size = 100)
	@OneToMany(mappedBy = "teacher"
//			,fetch = FetchType.EAGER
			) // fix2:
		//1) ineficient memorie/CPU/GC: TOT TIMPUL cand scoti Teacher, aduce si traingurile lui >
			// aduci Teacheri pe multe alte use caseuri? Daca da> ineficient.
			// "se poate si mai rau": daca in lista am 10000 de copii. > aici deja e modelu JPA varza: Parinte--1000+> Copil, asta nu tre' modelata cu List<Copil>
				// 	ci cu ChildRepo.streamByParentIdAndCreateDateBefore(parentId, date): Stream<Child>
				// stream().filter(pred) vs WHERE pred
		//2) ineficient cu NETWORK: N+1 queries problem: platesti timp de retea retea de N ori.
			// 		Quick fix: @BatchSize

	private List<Training> trainings = new ArrayList<>();
	// hibernate va pune un PersistentBag/Set pe post de List

	protected Teacher() {}
	
	public Teacher(String name) {
		this.name = name;
	}

}
