package com.sathish.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.sathish.dao.AdminService;
import com.sathish.model.Book;

public class AddBookAction extends ActionSupport
{
    private String bookName;
    private String authorName;
    private String genreName;
    private String description;
    private String publishedDate;
    private int totalCopies;
    private int maxBorrowDays;
    private int finePerDay;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public int getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(int finePerDay) {
        this.finePerDay = finePerDay;
    }


    public void addBook() throws IOException 
    {
        AdminService adminService = new AdminService();
        Book book = new Book(getBookName(), getAuthorName(), getGenreName(), getDescription(), getPublishedDate(), getTotalCopies(), getMaxBorrowDays(), getFinePerDay());
        
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        boolean status = adminService.addBook(book);
        if(status)
        {
        	out.write("bookaddedsuccess");
        }
        else
        {
        	out.write("bookaddederror");
        }
    }

}
