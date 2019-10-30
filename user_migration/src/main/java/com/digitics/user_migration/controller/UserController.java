package com.digitics.user_migration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitics.user_migration.bean.User;

@RestController
@RequestMapping("/User")
public class UserController {

	@PostMapping("/register")
	public ResponseEntity<User> userRegistration() {
		System.out.println("Calling register api:::");
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
