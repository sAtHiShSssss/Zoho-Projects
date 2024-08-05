package com.sathish.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;

public class UserLogoutAction extends ActionSupport
{
	@Override
	public String execute() throws Exception 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.invalidate();
		return SUCCESS;
	}
	

}
