package com.sathish.model;

public class DBUtils
{
    private static String url = "jdbc:postgresql://localhost:5432/LibraryManagement";
    private static String userName = "postgres";
    private static String userPass = "Sathish@123";
    public static String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public static String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public static String getUserPass() {
        return userPass;
    }
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
