package com.sathish.model;


public class BorrowedBook
{
    private String userId;
    private String bookId;
    private String bookName;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private int fine;

    public BorrowedBook(String userId, String bookId, String borrowDate, String dueDate, String returnDate, int fine) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }
    public BorrowedBook(String bookName, String borrowDate,  String dueDate, String returnDate, int fine) {
        this.bookName = bookName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }
    public String getBookName(){
        return bookName;
    }
    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(String returnDate) {
    	this.returnDate = returnDate;
    }
    public String getReturnDate() {
    	return returnDate;
    }
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}
