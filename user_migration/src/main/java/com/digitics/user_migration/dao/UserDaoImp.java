package com.digitics.user_migration.dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digitics.user_migration.bean.Role;
import com.digitics.user_migration.bean.User;

@Repository
public class UserDaoImp implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void userRegistration(List<User> userList,Set<Role> roleSet){
		System.out.println("User Registration dao metghod calling !!");
		 Session session = sessionFactory.getCurrentSession();
		 System.out.println("Session ::>>"+session);
			Iterator<User> listOfUser = userList.iterator();
			while(listOfUser.hasNext()) {
				User user1 = new User();
				User userObj=(User)listOfUser.next();
				if(!isEmailExists(session,userObj.getEmailAddress())) {
					session.beginTransaction();
					user1.setEmailAddress(userObj.getEmailAddress());;
					user1.setName(userObj.getName());
					user1.setRole(roleSet);
					session.save(user1);
					session.getTransaction().commit();
					System.out.println("data save successfully!!");
				}
			}
	}
	
	public boolean isEmailExists(Session session,String email) {
		 Query query = session.createQuery("select 1 from User e where e.emailAddress = :email");
		        query.setString("email", email );
		    return (query.uniqueResult() != null);
	}
	
	public void isRoleValid() {
		
	}
}
