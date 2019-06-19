package spring.training.first;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

//@AllArgsConstructor --- Lombok
@Service
class InConstructor {
	private final G g;
	public InConstructor(G g) {
		this.g = g;
	}
}

@Service
class InCampuri {
	@Autowired
	private G g;
	public void setG(G g) {
		this.g = g;
	}
	@PostConstruct
	public void initializari() {
	}
}


class AltaClasa {
	
	{
		//Context: creezi o instanta FARA Spring
		G g = new G();

		InCampuri x = new InCampuri();
		x.setG(g); // Sper sa nu uit sa fac asta
		
		//aici nu ai cum sa uiti sa-i dai dependentele
		InConstructor y = new InConstructor(g);
		
		// In teste, Mockito (@InjectMocks) face oricum injectia fie ca foloessti
		// constructor, fie campuri.
	}
}






@Service
class G {
	
}


@Service
class T {
	U u;
	
	@Autowired 
	public void setU(U u) {
		System.out.println("Injected u.t="+u.t);
		this.u = u;
	}
	@PostConstruct
	public void init() {
		System.out.println("U="+u);
	}

//	public T(U u) {
//		this.u = u;
//	}
	
}
@Service
class U {
	T t;
	
	@Autowired
	public void setT(T t) {
		System.out.println("Injected t.u="+t.u);
		this.t = t;
	}

//	public U(T t) {
//		this.t = t;
//	}
	@PostConstruct
	public void init() {
		System.out.println("T="+t);
	}
	
}

@Service
class M {
	@Autowired(required = false)
	public M(T t) {
		System.out.println("Constructor: T");
	}
	@Autowired(required = false)
	public M(T t, U u) {
		System.out.println("Constructor: TUS");
	}
}