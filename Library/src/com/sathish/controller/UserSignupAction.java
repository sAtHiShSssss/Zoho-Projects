package com.sathish.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.sathish.dao.*;
import com.sathish.model.User;

public class UserSignupAction extends ActionSupport
{
    private String name;
    private String email;
    private String password;
    private String phonenumber;
    private int age;
    private String address;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public void userSignup() throws IOException
    {
    	User user = new User(name,email,password,phonenumber,age,address);
        UserService userservice = new UserService();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter(); 
        Gson gson = new Gson();
        
        if(userservice.Signup(user))
        {
        	out.write(gson.toJson("SignupSuccess"));
        }
        else
        {
        	out.write(gson.toJson("SignupError"));
        }
    }

}
