package com.digitics.user_migration.dao;

import java.util.List;
import java.util.Set;

import com.digitics.user_migration.bean.Role;
import com.digitics.user_migration.bean.User;
import com.digitics.user_migration.dto.UserResponse;

public interface UserDao {

	public List<UserResponse> userRegistration(List<User> userList,Set<Role> roleSet);
}
