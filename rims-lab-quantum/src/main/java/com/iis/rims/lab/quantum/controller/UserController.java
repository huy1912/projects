package com.iis.rims.lab.quantum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@RequestMapping(value = "/user/getUsers", method = RequestMethod.GET)
	public ResponseEntity<String> getUsers() {
		return new ResponseEntity<String>("ABC", HttpStatus.OK);
	}
}
