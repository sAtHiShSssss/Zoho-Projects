package com.sathish.controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.AdminService;
import com.sathish.dao.UserService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class RenewBookAction extends ActionSupport
{
    private String bookID;
    public String getBookID(){
        return bookID;
    }
    public void setBookID(String bookID){
        this.bookID = bookID;
    }

    public void renewBook() throws SQLException, IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        boolean membership = (boolean) session.getAttribute("membership");
        HttpServletResponse response = ServletActionContext.getResponse();
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        if (!membership) {
            out.write(gson.toJson("Don't have membership"));
            return;
        }
        if (new UserService().RenewBook(bookID)) {
            out.write(gson.toJson("Book renewed succesfully"));
        } else {
            out.write(gson.toJson("Didn't find book"));
        }

    }
}
