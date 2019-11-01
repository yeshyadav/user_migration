package com.digitics.user_migration.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="roleid")
	private int roleId;
	
	@Column(name="Role_Name")
	private String roleName;

	@ManyToMany(targetEntity=User.class,mappedBy="role")
	private Set user;
	
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set getUser() {
		return user;
	}

	public void setUser(Set user) {
		this.user = user;
	}

}
