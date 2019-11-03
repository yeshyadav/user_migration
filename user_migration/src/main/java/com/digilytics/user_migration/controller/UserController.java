package com.digilytics.user_migration.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.user_migration.bean.User;
import com.digilytics.user_migration.service.UserService;

@RestController
@RequestMapping("/User")
@ControllerAdvice
public class UserController {

	@Autowired(required=true)
	UserService userService;
	
	@PostMapping(value="/register", consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String,List<Object>>> userRegistration(@RequestPart("file") MultipartFile file) throws IOException {
		System.out.println("Calling register api:::");
		System.out.println("file::"+file);
		System.out.println("Uploaded File: ");
		System.out.println("Name : " + file.getName());
		System.out.println("Type : " + file.getContentType());
		System.out.println("Name : " + file.getOriginalFilename());
		System.out.println("Size : " + file.getSize());
		Map<String,List<Object>> userRes = userService.userRegistration(file);
		return new ResponseEntity<Map<String, List<Object>>>(userRes,HttpStatus.OK);
	}
	  @ExceptionHandler({RuntimeException.class})
	    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
	        return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
	    }
	  private ResponseEntity<String> error(HttpStatus status, Exception e) {
	       // log.error("Exception : ", e);
	        return ResponseEntity.status(status).body(e.getMessage());
	    }
}
