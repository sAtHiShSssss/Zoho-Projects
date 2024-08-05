package com.sathish.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.sathish.dao.UserService;

public class SubscribeMembershipAction extends ActionSupport
{

	public void subscribe() throws IOException, SQLException
	{
		UserService userService = new UserService();
		HttpServletResponse response = ServletActionContext.getResponse();
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String message = userService.SubscribeMembership();
		
		String jsonData = gson.toJson(message);
		out.write(jsonData);
	}

}
