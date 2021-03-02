package com.example.demo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@EnableCaching
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
//@RefreshScope
//"GET /hello"
@RestController
class Hello {

	@Value("${in.folder.path}")
	File path;

	@Value("${welcome.welcomeMessage}")
	String message;

	@Value("${welcome.help.helpUrl}")
	URL helpUrl;

	@Value("${welcome.supportUrls}")
	List<URL> websiteCsv;

	@Value("#{'${welcome.supportUrls}'.split('\\s*;\\s*')}")
	List<URL> websiteCsvWithSpEL;

	List<URL> manualParse;

	@Autowired
	public void setManualParse( @Value("${welcome.supportUrls}") String s) {
		this.manualParse = new ArrayList<>();
		manualParse = Stream.of(s.split("\\s*;\\s*")).map(spec -> {
			try {
				return new URL(spec);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	}

	// <c:out value="${request['data'].listField[0]}" /> <c

	@PostConstruct
	public void checkInFolder() {
		if (!path.isDirectory()) {
			throw new IllegalArgumentException("Invalid in folder : " + path.getAbsolutePath());
		}
	}
//	@RequestMapping(method = RequestMethod.GET, path = "/hello")
	@GetMapping( "hello")
	@Cacheable("Stuff")
	public String method()  {
		String s = message + "" ;

		for (URL url : websiteCsvWithSpEL) {
			s += " >>> " + url;
		}
		for (URL url : manualParse) {
			s += " YYY " + url;
		}
		return "Hello!"  + path + " w "  + websiteCsv + "<br /> " + s ;
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