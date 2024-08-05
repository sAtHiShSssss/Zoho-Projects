package com.sathish.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

public class AdminLoginAction extends ActionSupport
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

    public void adminLogin() throws IOException
    {
    	Gson gson = new Gson();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	PrintWriter out = response.getWriter();
    	
        if(this.email.equals("admin@gmail.com") && this.password.equals("admin"))
        {
            HttpSession session = request.getSession();
            session.setAttribute("admin","admin");
            
            out.write(gson.toJson("AdminSuccess"));
        }
        else {
        	out.write(gson.toJson("AdminError"));
        }
    }

}
