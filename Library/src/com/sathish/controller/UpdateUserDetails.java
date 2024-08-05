package com.sathish.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.UserService;
import com.sathish.model.User;

public class UpdateUserDetails extends ActionSupport
{
	private String userId;
	private String fullName;
	private String email;
	private String password;
	private String phoneNumber;
	private int age;
	private String address;
	private String membershipEndDate;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMembershipEndDate() {
		return membershipEndDate;
	}

	public void setMembershipEndDate(String membershipEndDate) {
		this.membershipEndDate = membershipEndDate;
	}

	public void updateDetails() throws IOException, SQLException
	{
		User user = new User(fullName,password,phoneNumber,age,address);
		new UserService().updateUserDetails(user);
	}

}
