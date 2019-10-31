package com.digitics.user_migration.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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
		//userService.userRegistration();
		try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).build();
			List<String[]> allData = csvReader.readAll();
			// Print Data.
			HashMap<String, ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
			int counter = 0;
			ArrayList<String> al = new ArrayList<String>();
			for (String[] row : allData) {
				for (String cell : row) {
					if (counter == 0) {
						hm.put(cell,al);
					}else if(hm.containsKey(hm.get("Email_Address")) || hm.containsKey(hm.get("Name")) || hm.containsKey(hm.get("Roles"))) {
					}
					System.out.print(cell + "\t");
				}
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User>(HttpStatus.OK);
	}
}
