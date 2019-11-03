package com.digilytics.user_migration.dto;

import java.util.Map;

public class UserResponse {

	private int noOfRowPass;
	private int noOfRowFail;
	Map<String,String> errorMap;
	
	public int getNoOfRowPass() {
		return noOfRowPass;
	}
	public void setNoOfRowPass(int noOfRowPass) {
		this.noOfRowPass = noOfRowPass;
	}
	public int getNoOfRowFail() {
		return noOfRowFail;
	}
	public void setNoOfRowFail(int noOfRowFail) {
		this.noOfRowFail = noOfRowFail;
	}
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
}
