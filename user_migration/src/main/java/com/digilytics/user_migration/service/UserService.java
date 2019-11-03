package com.digilytics.user_migration.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.digilytics.user_migration.dto.UserResponse;

public interface UserService {

	public List<UserResponse> userRegistration(MultipartFile file) throws IOException;
	
}
