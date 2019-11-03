package com.digilytics.user_migration.dao;

import java.util.List;
import java.util.Set;

import com.digilytics.user_migration.bean.Role;
import com.digilytics.user_migration.bean.User;
import com.digilytics.user_migration.dto.UserResponse;

public interface UserDao {

	public UserResponse userRegistration(List<User> userList,Set<Role> roleSet);
}
