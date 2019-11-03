package com.digitics.user_migration.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digitics.user_migration.bean.Role;
import com.digitics.user_migration.bean.User;
import com.digitics.user_migration.dao.UserDao;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	public void userRegistration(MultipartFile file) throws IOException {
		
		//try {
			Reader reader = new InputStreamReader(file.getInputStream());
			CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();
			// Print Data.
			//HashMap<String, ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
			List<User> userList = new ArrayList<User>();
			//List roleList = new ArrayList();
			Set<Role> roleSet = new LinkedHashSet<>();
			int rowNum=1;
			for (String[] row : allData) {
				User user = new User();
				Role role = new Role(); 
				user.setEmailAddress(row[0]);
				user.setName(row[1]);
				user.setTotalRowNum(allData.size());
				user.setRowNum(rowNum);
				//user.setRoles(row[2]);
				role.setRoleName(row[2]);
				//role.setRoleName(row[2]);
				userList.add(user);
				roleSet.add(role);
				rowNum++;
				//userList.add(role);
			}
			userDao.userRegistration(userList,roleSet);
			/*HashMap<String, List> hashmap = new HashMap<String,List>();
			Iterator<User> listOfUser = userList.iterator();
			ArrayList emailList = new ArrayList();
			while(listOfUser.hasNext()) {
				User userObj=(User)listOfUser.next();
				userObj.getEmailAddress();
				userObj.getName();
				userObj.getRoles();
				hashmap.put(key, value);
			}*/
			
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
	}
	
	public void isUserValidate() {
		
	}
}
