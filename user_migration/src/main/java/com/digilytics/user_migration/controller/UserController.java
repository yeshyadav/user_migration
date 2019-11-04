package com.digilytics.user_migration.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.user_migration.service.UserService;

@RestController
@RequestMapping("/User")
@ControllerAdvice
public class UserController {

	@Autowired(required=true)
	UserService userService;
	
	@Value("${spring.application.image_root}")
	String imageRootPath;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping(value="/register", consumes = { "multipart/form-data" })
	public ResponseEntity<Map<String,List<Object>>> userRegistration(@RequestPart("file") MultipartFile file) throws IOException {
		LOGGER.info("Call register Api::"+"File Name:"+file.getName()+"<<content type>>"+file.getContentType()+""+file.getSize());
		Map<String,List<Object>> userRes = userService.userRegistration(file);
		return new ResponseEntity<Map<String, List<Object>>>(userRes,HttpStatus.OK);
	}
	
	@GetMapping("/download/{fileName:.+}")
	public void downloadCsvFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {
		LOGGER.info("Call download Api::<<file full path:>>"+imageRootPath+fileName);
		File file = new File(imageRootPath+fileName);
		System.out.println(">>>>>>>"+file.getName());
		if (file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
