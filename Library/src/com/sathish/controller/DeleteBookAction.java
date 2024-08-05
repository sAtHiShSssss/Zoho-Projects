package com.sathish.controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.AdminService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class DeleteBookAction extends ActionSupport
{
    private String bookId;
    public void setBookId(String bookId){
        this.bookId = bookId;
    }
    public String getBookId(){
        return bookId;
    }
    
    public void deleteBook() throws SQLException, IOException
    {
    	Gson gson = new Gson();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	PrintWriter out = response.getWriter();
    	boolean status = new AdminService().deleteBook(this.bookId);
    	if(status)
    	{
    		out.write("deletebooksuccess");

    	}
    	else
    	{
    		out.write("deletebookerror");
    	}
    }
}
