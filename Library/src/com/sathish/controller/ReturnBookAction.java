package com.sathish.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.UserService;

public class ReturnBookAction extends ActionSupport
{
    private String bookId;
    public void setBookId(String bookId) {
    	this.bookId = bookId;
    }
    public String getBookId() {
    	return bookId;
    }
    public void returnBook() throws SQLException, IOException
    {
    	new UserService().returnBook(bookId);
    }
}
