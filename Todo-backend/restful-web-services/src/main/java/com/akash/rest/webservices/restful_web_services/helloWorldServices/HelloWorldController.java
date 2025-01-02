package com.akash.rest.webservices.restful_web_services.helloWorldServices;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

//below annotation tells spring MVC that this class will handle HTTP requests.

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HelloWorldController {

//	@RequestMapping(method=RequestMethod.GET, path = "/hello")
	@GetMapping(path = "hello")
	public String helloWorld() {
		return "Hello World!";
	}

	@GetMapping(path = "helloo")
	public HelloWorld helloWorldBean() {
		return new HelloWorld("hello there. this is helloWorldBean");
	}
	@GetMapping("hello/{name}")
	public String helloWorldWithPathVariable(@PathVariable String name) {
		return new String("Hello!! I got your name \n"+ name);
	}
	

}
