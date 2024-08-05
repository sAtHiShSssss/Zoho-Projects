package com.sathish.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.sathish.dao.UserService;

public class UserSigninAction extends ActionSupport
{
    private String email;
    private String password;
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

    public void userSignin() throws IOException
    {
        UserService userservice = new UserService();
        Gson gson = new Gson();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	PrintWriter out = response.getWriter();
    	
        if(userservice.Signin(email,password))
        {
            out.write(gson.toJson("SignInSuccess"));
        }
        else
        {
        	out.write(gson.toJson("SignInError"));
        }
    }

}
