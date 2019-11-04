package com.digilytics.user_migration.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	public Map<String, List<Object>>  userRegistration(MultipartFile file) throws IOException {
		
			System.out.println("file data::"+file);
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();
			Map<String,List<Object>> finalRes = new LinkedHashMap<String,List<Object>>();
			//int rowNumPass=1;
			//int rowNumFail=1;
			//String filePath = "";
			//new ArrayList<>().add(filePath)
			int intRowCount = 2; 
			List<Object> noOfRowsPassCount = new ArrayList<Object>();
			List<Object> noOfRowsFailCount = new ArrayList<Object>();
			List<User> failRecordList = new ArrayList<User>();
			System.out.println("All data size::>>> "+allData.size());
			List<Object> filPathList = null;
			for (String[] row : allData) {
				List<User> userList = new ArrayList<User>();
				Set<Role> roleSet = new LinkedHashSet<>();
				String[] strErrorArr = new String[row.length + 1];
				User user = new User();
				Role role = new Role(); 
				boolean emailErrorFlag = false;
				if(!isEmailValid(row[0])) {
					emailErrorFlag = true;
					user.setEmailAddress(row[0]);
					user.setName(row[1]);
					user.setRoles(row[2]);
					user.setErrors("Invalid Email");
					/*strErrorArr[0]=row[0];
					strErrorArr[1]=row[1];
					strErrorArr[2]=row[2];
					strErrorArr[3]="Invalid Email";*/
					//user.setNoOfRowFail(intRowCount);
					role.setRoleName(row[2]);
					failRecordList.add(user);
					//noOfRowsFailCount.add(String.valueOf(user.getNoOfRowFail()));
					noOfRowsFailCount.add(intRowCount);
				}if(!isRoleValid(row[2])) {
					user.setEmailAddress(row[0]);
					user.setName(row[1]);
					user.setRoles(row[2]);
					/*strErrorArr[0]=row[0];
					strErrorArr[1]=row[1];
					strErrorArr[2]=row[2];*/
					if(user.getEmailAddress() != null) {
						user.setErrors(user.getErrors()+"#Invalid Role");
					}else {
						user.setErrors("Invalid Role");
						//user.setNoOfRowFail(intRowCount);
						failRecordList.add(user);
						//noOfRowsFailCount.add(String.valueOf(user.getNoOfRowFail()));
						noOfRowsFailCount.add(intRowCount);
					}
				}else if(!emailErrorFlag){
					user.setEmailAddress(row[0]);
					user.setName(row[1]);
					user.setTotalRowNum(allData.size());
					user.setNoOfRowPass(intRowCount);
					user.setRoles(row[2]);
					role.setRoleName(row[2]);
					userList.add(user);
					roleSet.add(role);
					UserResponse userResList = userDao.userRegistration(userList,roleSet);
					User newUser = new User();
					if(userResList.getErrorMap().containsKey("Errors")) {
						newUser.setEmailAddress(userResList.getErrorMap().get("Email"));
						newUser.setName(userResList.getErrorMap().get("Name"));
						newUser.setRoles(userResList.getErrorMap().get("Roles"));
						newUser.setErrors(userResList.getErrorMap().get("Errors"));
						failRecordList.add(newUser);
						noOfRowsFailCount.add(intRowCount);
					}else {
						noOfRowsPassCount.add(intRowCount);
					}
					//noOfRowsPassCount.add(String.valueOf(intRowCount));
				}
				intRowCount++;
			}
			if(!failRecordList.isEmpty()) {
				filPathList = createErrorFile(failRecordList);
			}
			finalRes.put("no_of_rows_parsed",noOfRowsPassCount);
			finalRes.put("no_of_rows_failed", noOfRowsFailCount);
			finalRes.put("error_file_url", filPathList);
			return finalRes;
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
		
 	 List<Object> createErrorFile(List<User> failRowsList) throws IOException {
		List<Object> filePathList = new ArrayList<Object>();
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
		Iterator<User> itrObj =  failRowsList.iterator();
		while(itrObj.hasNext()) {
			String[] errorArr = new String[5];
			User user= (User)itrObj.next();
			errorArr[0] = user.getEmailAddress();
			errorArr[1] = user.getName();
			errorArr[2] = user.getRoles();
			errorArr[3] = user.getErrors();
			writer.writeNext(errorArr);
		}
		writer.close();
		filePathList.add(filePath);
		return filePathList;
	 }
}
