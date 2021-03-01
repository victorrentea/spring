package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

//"GET /hello"
@RestController
class Hello {
//	@RequestMapping(method = RequestMethod.GET, path = "/hello")
	@GetMapping( "hello")
	public String method() {
		return "Hello!";
	}
}



@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
class CustomerController {
	@GetMapping
	public List<CustomerDto> getAllCustomers() {
		return null;
	}
	@GetMapping("{id}")
	public CustomerDto getAllCustomers(@PathVariable long id) {
		return null;
	}

	@PostMapping
	public void createCustomer(@RequestBody CustomerDto dto) {
		System.out.println(dto);
	}

	@PutMapping("{id}")
	public void overwriteCustomer( @PathVariable long id, @RequestBody CustomerDto dto) {
		System.out.println(dto);
	}
	@PutMapping("{id}/phone")
	public void setCustomerPhone( @PathVariable long id, @RequestBody CustomerPhoneDto dto) {
//		System.out.println(dto);
	}
	@PutMapping("customers/{id}/shippindDetails")
	public void setCustomerPhone( @PathVariable long id, @RequestBody CustomerDto dto) {
//		System.out.println(dto);
	}

}

//@Entity
class Customer {
//	ShippingDetails
}
@Data
class CustomerDto {
	private String name;
}
@Data
class CustomerPhoneDto {
	private String phone;
}