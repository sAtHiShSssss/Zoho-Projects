package com.sathish.controller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.AdminService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class FetchBooksAction extends ActionSupport
{
	public void fetchBooks() throws SQLException, IOException
	{
		AdminService adminService = new AdminService();
        String jsonData = adminService.fetchAllBooks();
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        out.write(jsonData);
	}
}

