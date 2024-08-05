package com.sathish.model;

public class User
{
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private int age;
    private String address;
    private String membershipEndDate;

    public User(String userId, String fullName, String email, String password, String phoneNumber, int age, String address, String membershipEndDate) 
    {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
        this.membershipEndDate = membershipEndDate;
    }
    public User(String userId, String fullName, String email, String phoneNumber, int age, String address, String membershipEndDate)
    {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
        this.membershipEndDate = membershipEndDate;
    }
    public User(String fullName, String email, String password, String phoneNumber, int age, String address) 
    {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.address = address;
    }
    

    public User(String fullName, String password, String phoneNumber, int age, String address) 
    {
		this.fullName = fullName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.address = address;
	}


	public String getUserId() {
        return userId;
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
}
