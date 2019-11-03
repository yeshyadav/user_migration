package com.digitics.user_migration.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	public void userRegistration(MultipartFile file) throws IOException;
	
}
