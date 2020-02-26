package victor.training.springdemo;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private String address;
	private List<String> phones = new ArrayList<>();

	public String getName() {
		return name;
	}

	public Customer setName(String name) {
		this.name = name;
		return this;
	}

	public Customer setAddress(String address) {
		this.address = address;
		return this;
	}

	public Customer addPhone(String phone) {
		phones.add(phone);
		return this;
	}

	static {
		new Customer()
				.setName("jdoe")
				.setAddress("BostonShine")
				.addPhone("8989989");
	}
}
