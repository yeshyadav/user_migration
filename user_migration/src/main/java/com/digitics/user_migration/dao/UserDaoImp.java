package com.digitics.user_migration.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digitics.user_migration.bean.Role;
import com.digitics.user_migration.bean.User;
import com.digitics.user_migration.dto.UserResponse;

@Repository
public class UserDaoImp implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<UserResponse> userRegistration(List<User> userList, Set<Role> roleSet) {
		System.out.println("User Registration dao metghod calling !!");
		Session session = sessionFactory.getCurrentSession();
		System.out.println("Session ::>>" + session);
		Map<String,String> errorMap = new HashMap<String,String>();
		Iterator<User> listOfUser = userList.iterator();
		List<UserResponse> userResList = new ArrayList<UserResponse>();
		UserResponse userResponse = new UserResponse();
		// int counter = 0;
		while (listOfUser.hasNext()) {
			User user1 = new User();
			User userObj = (User) listOfUser.next();
			if(!isEmailExists(session,userObj.getEmailAddress())) {
				session.beginTransaction();
				user1.setEmailAddress(userObj.getEmailAddress());
				user1.setName(userObj.getName());
				user1.setRole(roleSet);
				user1.setTotalRowNum(userObj.getTotalRowNum());
				// user1.setRowNum(userObj.getRowNum());
				Object obj = session.save(user1);
				if (obj.toString().equals("0"))
					userResponse.setNoOfRowFail(userObj.getRowNum());
				userResponse.setNoOfRowPass(userObj.getRowNum());
				session.getTransaction().commit();
				
			}else {
				errorMap.put("Email", user1.getEmailAddress());
				errorMap.put("Name", user1.getName());
				errorMap.put("Roles", user1.getRoles());
				errorMap.put("Errors", "This email already exists !");
				System.out.println("errormap::"+errorMap);
				userResponse.setErrorMap(errorMap);
			}
			
			userResList.add(userResponse);
		}
		System.out.println("lisrt size::"+userResList.size());
		return userResList;
	}
	
	@SuppressWarnings("deprecation")
	public boolean isEmailExists(Session session,String email) {
		 Query<?> query = session.createQuery("select 1 from User e where e.emailAddress = :email");
		        query.setString("email", email );
		    return (query.uniqueResult() != null);
	}
	
	public void isRoleValid() {
		
	}
}
