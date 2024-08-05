package com.sathish.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.sathish.dao.AdminService;

public class FetchBorrowedBooks extends ActionSupport
{

	public void borrowedBooks() throws IOException, SQLException
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		String jsonData = new AdminService().fetchBorrowedBooks();
		out.write(jsonData);
	}
}
