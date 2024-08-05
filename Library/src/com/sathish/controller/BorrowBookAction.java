package com.sathish.controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.UserService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class BorrowBookAction extends ActionSupport
{
    private String bookId;
    public String getBookId(){
        return bookId;
    }
    public void setBookId(String bookId){
        this.bookId = bookId;
    }

    public void borrowBook() throws SQLException, IOException
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        boolean membership = (boolean)session.getAttribute("membership");

        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        System.out.println(membership);
        Gson gson = new Gson();
        if(!membership)
        {
            out.write("Don't have membership to borrow book");
            return;
        }

        boolean status = new UserService().borrowBook(this.bookId);
        if(status)
        {
            out.write("Book borrowed successfully");
        }
        else
        {
        	out.write("Didn't borrow book or book doesn't exist");
        }
    }
}
