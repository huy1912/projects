package com.iis.rims.lab.quantum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {
	
	@Value("${welcome.message:test}")
	private String message;
	
	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		model.put("appName", "rims lab");
		return "index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		System.err.println("Welcome page...............");
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody SubmitOrder submitOrder(@RequestBody SubmitOrder order) {
		return order;
	}
}
