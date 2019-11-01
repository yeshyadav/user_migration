package com.digitics.user_migration.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;

@Component
@Entity
@Table(name="user",uniqueConstraints=@UniqueConstraint(columnNames={"Email_Address"}))
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userid")
	private int userId;
	
	@Column(name="Email_Address")
	private String emailAddress;
	
	@Column(name="Name")
	private String name;
	
	@Transient
	private int rowNum;
	
	@Transient
	private String Roles;
	
	@ManyToMany(targetEntity=Role.class,cascade=CascadeType.ALL)
	@JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id_fk",referencedColumnName="userId"),inverseJoinColumns=@JoinColumn(name="role_id_fk",referencedColumnName="roleId"))
	private Set role;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoles() {
		return Roles;
	}

	public void setRoles(String roles) {
		Roles = roles;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public Set getRole() {
		return role;
	}

	public void setRole(Set role) {
		this.role = role;
	}
}
