package com.securedService.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class TestController {
	
	List<Integer> linum = new ArrayList<>();

	
	
	public TestController() {
		this.linum.add(12);
		this.linum.add(22);
	}

	@GetMapping("/test")
	public String testApi(HttpSession ses) {
		return "This is working "+ses.getId() ;
	}
	
	@GetMapping("/getvalue")
	public List<Integer> getValues() {
		return linum;
	}
	
	@GetMapping("/csrf-get")
	public CsrfToken getCsrfToken(HttpServletRequest req) {
		return (CsrfToken)req.getAttribute("_csrf");
	}
	
	@PostMapping("/entryvalue/{value}")
	public void entryValue(@PathVariable int value) {
		linum.add(value);
	}

}
