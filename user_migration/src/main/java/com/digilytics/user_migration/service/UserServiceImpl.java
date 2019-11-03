package com.digilytics.user_migration.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digilytics.user_migration.bean.Role;
import com.digilytics.user_migration.bean.User;
import com.digilytics.user_migration.dao.UserDao;
import com.digilytics.user_migration.dto.UserResponse;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public List<UserResponse> userRegistration(MultipartFile file) throws IOException {
		
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();
			List<User> userList = new ArrayList<User>();
			Set<Role> roleSet = new LinkedHashSet<>();
			int rowNum=1;
			for (String[] row : allData) {
				String[] strErrorArr = new String[row.length + 1];
				User user = new User();
				Role role = new Role(); 
				if(!isEmailValid(row[0])) {
					strErrorArr[0]=row[0];
					strErrorArr[1]=row[1];
					strErrorArr[2]=row[2];
					strErrorArr[3]="Invalid Email";
					role.setRoleName(row[2]);
				}if(!isRoleValid(row[2])) {
					strErrorArr[0]=row[0];
					strErrorArr[1]=row[1];
					strErrorArr[2]=row[2];
					if(strErrorArr[3] != null) {
						strErrorArr[3]=strErrorArr[3]+"#Invalid Role";
					}else {
						strErrorArr[3]="Invalid Role";
					}
					createErrorFile(strErrorArr);
				}
				else {
					user.setEmailAddress(row[0]);
					user.setName(row[1]);
					user.setTotalRowNum(allData.size());
					user.setRowNum(rowNum);
					user.setRoles(row[2]);
					role.setRoleName(row[2]);
					userList.add(user);
					roleSet.add(role);
					rowNum++;
				}
			}
			List<UserResponse> userResList = userDao.userRegistration(userList,roleSet);
			Iterator<UserResponse> userLisrItr = userResList.iterator();
			while(userLisrItr.hasNext()) {
				UserResponse userResObj = (UserResponse)userLisrItr.next();
				if(userResObj.getErrorMap().isEmpty()) {
					
				}
			}
			return userResList;
	}
	
	  boolean isEmailValid(String email) {
	      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
	   }

	 boolean isRoleValid(String role) {
		String isStrValid = "";
		String[] strArr = role.split("#");
		int len = strArr.length;
		if (len < 2) {
			return false;
		}
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[0].toString().isEmpty()) {
				isStrValid += "false";
			}
		}
		if (isStrValid.isEmpty()) {
			return true;
		}
		return false;
	}
		
	String createErrorFile(String[] errorArr) throws IOException {
		String filePath = "E:\\java\\UserBulkFile.csv";
		System.out.println(filePath);
		File file = new File(filePath);
		// create FileWriter object with file as parameter
		FileWriter outputfile = new FileWriter(file);
		// create CSVWriter object filewriter object as parameter
		CSVWriter writer = new CSVWriter(outputfile);
		// header to csv
		String[] header = { "Email", "Name", "Role", "Errors" };
		writer.writeNext(header);
		// added error data
		writer.writeNext(errorArr);
		writer.close();
		return filePath;
	 }
}
