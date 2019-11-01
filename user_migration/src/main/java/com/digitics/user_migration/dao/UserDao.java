package com.digitics.user_migration.dao;

import java.util.List;
import java.util.Set;

import com.digitics.user_migration.bean.Role;
import com.digitics.user_migration.bean.User;

public interface UserDao {

	public void userRegistration(List<User> userList,Set<Role> roleSet);
}
