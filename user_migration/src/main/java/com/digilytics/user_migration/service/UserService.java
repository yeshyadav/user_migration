package com.digilytics.user_migration.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.digilytics.user_migration.dto.UserResponse;

public interface UserService {

	public Map<String, List<Object>> userRegistration(MultipartFile file) throws IOException;
	
}
