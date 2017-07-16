package com.iis.rims.lab.quantum.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
