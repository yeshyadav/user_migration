package com.digitics.user_migration.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;

@Repository
public class UserDaoImp implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void userRegistration() {
		System.out.println("User Registration dao metghod calling !!");
		 Session session = sessionFactory.getCurrentSession();
		 System.out.println("Session ::>>"+session);
	}
}
