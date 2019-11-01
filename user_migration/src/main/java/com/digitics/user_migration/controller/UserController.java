package com.digitics.user_migration.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitics.user_migration.bean.User;
import com.digitics.user_migration.service.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired(required=true)
	UserService userService;
	
	@PostMapping(value="/register", consumes = { "multipart/form-data" })
	public ResponseEntity<User> userRegistration(@RequestPart("file") MultipartFile file) throws IOException {
		System.out.println("Calling register api:::");
		System.out.println("file::"+file);
		System.out.println("Uploaded File: ");
		System.out.println("Name : " + file.getName());
		System.out.println("Type : " + file.getContentType());
		System.out.println("Name : " + file.getOriginalFilename());
		System.out.println("Size : " + file.getSize());
		userService.userRegistration(file);
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
