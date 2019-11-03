package com.digilytics.user_migration.dao;

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

import com.digilytics.user_migration.bean.Role;
import com.digilytics.user_migration.bean.User;
import com.digilytics.user_migration.dto.UserResponse;

@Repository
public class UserDaoImp implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public UserResponse userRegistration(List<User> userList, Set<Role> roleSet) {
		System.out.println("User Registration dao metghod calling !!");
		Session session = sessionFactory.getCurrentSession();
		System.out.println("Session ::>>" + session);
		Map<String,String> errorMap = new HashMap<String,String>();
		Iterator<User> listOfUser = userList.iterator();
		List<UserResponse> userResList = new ArrayList<UserResponse>();
		UserResponse userResponse = new UserResponse();
		while (listOfUser.hasNext()) {
			User user1 = new User();
			User userObj = (User) listOfUser.next();
			if(!isEmailExists(session,userObj.getEmailAddress())) {
				session.beginTransaction();
				user1.setEmailAddress(userObj.getEmailAddress());
				user1.setName(userObj.getName());
				user1.setRole(roleSet);
				session.save(user1);
				session.getTransaction().commit();
			}else {
				errorMap.put("Email", userObj.getEmailAddress());
				errorMap.put("Name", userObj.getName());
				errorMap.put("Roles", userObj.getRoles());
				errorMap.put("Errors", "This email already exists !");
				System.out.println("errormap::"+errorMap);
				userResponse.setErrorMap(errorMap);
			}
			//userResList.add(userResponse);
		}
		userResponse.setErrorMap(errorMap);
		System.out.println("userResponse size::"+userResponse);
		return userResponse;
	}
	
	@SuppressWarnings("deprecation")
	public boolean isEmailExists(Session session,String email) {
		 Query<?> query = session.createQuery("select 1 from User e where e.emailAddress = :email");
		        query.setString("email", email );
		    return (query.uniqueResult() != null);
	}
}
