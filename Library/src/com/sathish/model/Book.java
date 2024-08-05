package com.sathish.model;


public class Book
{
    private String bookId;
    private String bookName;
    private String authorId;
    private String genreId;
    private String authorName;
    private String genreName;
    private String description;
    private String publishedDate;
    private int availableCopies;
    private int totalCopies;
    private int maxBorrowDays;
    private int finePerDay;

    public Book(String bookName,String authorName,String genreName, String description, String publishedDate, int totalCopies, int maxBorrowDays, int finePerDay)
    {
        this.bookName = bookName;
        this.authorName = authorName;
        this.genreName = genreName;
        this.description = description;
        this.publishedDate = publishedDate;
        this.totalCopies = totalCopies;
        this.maxBorrowDays = maxBorrowDays;
        this.finePerDay = finePerDay;
    }
    public Book(String bookId,String bookName,String authorName,String genreName, String description, String publishedDate, int availableCopies, int totalCopies, int maxBorrowDays, int finePerDay)
    {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.genreName = genreName;
        this.description = description;
        this.publishedDate = publishedDate;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.maxBorrowDays = maxBorrowDays;
        this.finePerDay = finePerDay;
    }

	public String getBookId(){
        return bookId;
    }
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
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
}
