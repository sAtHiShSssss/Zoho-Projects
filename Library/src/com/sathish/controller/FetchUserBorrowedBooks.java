package com.sathish.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.UserService;

public class FetchUserBorrowedBooks extends ActionSupport
{

	public void fetchBooks() throws SQLException, IOException
	{
		new UserService().fetchBorrowedBooks();
	}
	
}
