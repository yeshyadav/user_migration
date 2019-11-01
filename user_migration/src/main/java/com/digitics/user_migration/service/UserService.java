package com.digitics.user_migration.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

	public void userRegistration(MultipartFile file);
	
}
