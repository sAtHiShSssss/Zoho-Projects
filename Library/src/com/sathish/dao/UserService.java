package com.sathish.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.sathish.model.BorrowedBook;
import com.sathish.model.DBUtils;
import com.sathish.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UserService
{
	public Connection getConnection()
    {
        try
        {
            //create connection to database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DBUtils.getUrl(),DBUtils.getUserName(),DBUtils.getUserPass());
            if(connection != null)
            {
                System.out.println("Connection Established");
            }
            return connection;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
	
	public boolean Signup(User user)
    {
        try
        {
            //insert new user in the database
            Connection connection = getConnection();
            String query = "insert into users (fullName,email,password,phoneNumber,age,address) values(?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getFullName());
            statement.setString(2,  user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4,  user.getPhoneNumber());
            statement.setInt(5,  user.getAge());
            statement.setString(6,  user.getAddress());
            int result = statement.executeUpdate();
            if(result > 0)
            {
                //Get id of new inserted user
                query = "select userId from users where email = ? ";
                statement = connection.prepareStatement(query);
                statement.setString(1, user.getEmail());
                ResultSet rs = statement.executeQuery();
                if(rs.next()) 
                {
                	SetSession(rs.getString(1));
                }
                return true;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean Signin(String email, String password)
    {
        try
        {
            //check user exist or not in database
            Connection connection = getConnection();
            String query = "select userId from users where email = ? and password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            if(rs.next())
            {
                //Set session for that user
                String userId = rs.getString(1);
                SetSession(userId);
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
	
    public boolean borrowBook(String bookId) throws SQLException
    {
        //Current user session
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        String userId = (String)session.getAttribute("userId");

        //get book details from book table
        Connection connection = getConnection();
        String query = "select * from borrowedBooks where bookId=? and userId=? and returnDate is null ";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, bookId);
        statement.setString(2,userId);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            return false;
        }
        query = "select * from books where bookId = ? ";
        statement = connection.prepareStatement(query);
        statement.setString(1, bookId);
        rs = statement.executeQuery();
        
        if(rs.next() && rs.getInt(7) > 0)
        {
            //Update available books in books table
            query = "update books set availableCopies = ? where bookId = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, (rs.getInt(7)-1));
            statement.setString(2, bookId);
            statement.executeUpdate();

            //insert borrowedBook details in borrowedBook table
            LocalDate borrowDate = LocalDate.now();
            LocalDate lastReturnDate = borrowDate.plusDays(rs.getInt(9));
            query = "insert into borrowedBooks(bookId,userId,borrowDate,lastReturnDate) values(?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, rs.getString(1));
            statement.setString(2, userId);
            statement.setString(3, borrowDate.toString());
            statement.setString(4, lastReturnDate.toString());

            if(statement.executeUpdate() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean RenewBook(String bookId) throws SQLException
    {
        //current user session
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        String userId = (String)session.getAttribute("userId");

        //get user borrowed book details from borrowedBook table
        Connection connection = getConnection();
        String query = "select * from borrowedBooks where bookId = ? and userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, bookId);
        statement.setString(2, userId);
        ResultSet rs = statement.executeQuery();

        if(rs.next())
        {
            //get maximum borrow days from books table
            LocalDate currDate = LocalDate.now();
            query = "select maxBorrowDays from books where bookId = ? ";
            statement = connection.prepareStatement(query);
            statement.setString(1, bookId);
            rs = statement.executeQuery();
            int maxBorrowDays = 0;
            if(rs.next())
            {
            	maxBorrowDays = rs.getInt(1);
        	}

            //update last return date in borrowed books for that user
            LocalDate lastReturnDate = currDate.plusDays(maxBorrowDays);
            query = "update borrowedBooks set lastReturnDate = ? where bookId = ? and userId = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, lastReturnDate.toString());
            statement.setString(2, bookId);
            statement.setString(3, userId);
            
            if(statement.executeUpdate() > 0)
            	return true;
        }
        return false;
    }
    public void returnBook(String bookId) throws SQLException, IOException
    {
        //current user session
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String userId = (String)session.getAttribute("userId");

        Connection connection = getConnection();
        String query = "select * from borrowedBooks where bookId = ? and userId = ? and returnDate is null";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,  bookId);
        statement.setString(2,  userId);
        ResultSet rs = statement.executeQuery();
        if(rs.next())
        {
        	LocalDate lastReturnDate = LocalDate.parse(rs.getString(4));
        	query = "select availableCopies,finePerDay from books where bookId = ?";
        	statement = connection.prepareStatement(query);
        	statement.setString(1, bookId);
        	rs = statement.executeQuery();
        	if(rs.next())
        	{
	        	int availableCopies = rs.getInt(1);
	        	int finePerDay = rs.getInt(2);
	        	LocalDate currDate = LocalDate.now();
	        	int diffInDays = (int)ChronoUnit.DAYS.between(lastReturnDate,currDate);
	        	int totalFine = 0;
	        	if(diffInDays > 0)
	        	{
	        		totalFine = finePerDay * diffInDays;
	        		out.write(gson.toJson("Book returned successfully!\nLate Return fine : Rs."+totalFine));
	        	}
	        	else
	        	{
	        		out.write(gson.toJson("Book return successfully"));
	        	}
	        	query = "update borrowedBooks set returnDate = ?, fine = ? where bookId = ? and userId = ?";
	        	statement = connection.prepareStatement(query);
	        	statement.setString(1, currDate.toString());
	        	statement.setInt(2, totalFine);
	        	statement.setString(3,  bookId);
	        	statement.setString(4,  userId);
	        	if(statement.executeUpdate() > 0)
	        	{
	        		query = "update books set availableCopies = ? where bookId = ?";
	        		statement = connection.prepareStatement(query);
	        		statement.setInt(1, (availableCopies+1));
	        		statement.setString(2,  bookId);
	        		statement.executeUpdate();
	        	}
        	}
        }
        else
        {
        	out.write(gson.toJson("Didn't find book"));
        }
    }
    
    public void fetchBorrowedBooks() throws SQLException, IOException
    {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpSession session = request.getSession();
    	String userId = (String) session.getAttribute("userId");
    	HttpServletResponse response = ServletActionContext.getResponse();
    	PrintWriter out = response.getWriter();
    	Gson gson = new Gson();
    	List<BorrowedBook> userBooks = new ArrayList<>();
    	Connection connection = getConnection();
    	String query = "select b.bookName,bb.borrowDate,bb.dueDate,case when bb.returnDate is not null then returnDate else 'Not Return' end as returnDate,bb.fine from books b inner join borrowedBooks bb on b.bookId=bb.bookId where bb.userId = ? order by returnDate desc";
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1, userId);
    	ResultSet rs = statement.executeQuery();
    	while(rs.next())
    	{
    		userBooks.add(new BorrowedBook(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5)));
    	}
    	out.write(gson.toJson(userBooks));
    }
    
    public String SubscribeMembership() throws SQLException
    {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpSession session = request.getSession();
    	String userId = (String)session.getAttribute("userId");
    	boolean member = (boolean)session.getAttribute("membership");
    	if(member)
    	{
    		return "Already member";
    	}
    	Connection connection = getConnection();
    	String query = "update users set membershipEndDate = ? where userId = ?";
    	LocalDate date = LocalDate.now().plusDays(30);
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1, date.toString());
    	statement.setString(2, userId);
    	if(statement.executeUpdate() > 0) {
    		session.setAttribute("membership", true);
    		return "success";
    	}
    	return"error";
    }
    
    public void getUserDetails() throws SQLException, IOException
    {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpSession session = request.getSession();
    	PrintWriter out = response.getWriter();
    	String userId = (String)session.getAttribute("userId");
    	Gson gson = new Gson();
    	Connection connection = getConnection();
    	String query = "select * from users where userId = ?";
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1,  userId);
    	ResultSet rs = statement.executeQuery();
    	if(rs.next())
    	{
    		User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7),rs.getString(8));
    		out.write(gson.toJson(user));
    		return;
    	}
    	
    }
    
    public void updateUserDetails(User user) throws IOException, SQLException
    {
    	System.out.println("Hello");
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpServletResponse response = ServletActionContext.getResponse();
    	HttpSession session = request.getSession();
    	String userId = (String)session.getAttribute("userId");
    	PrintWriter out = response.getWriter();
    	Gson gson = new Gson();

    	Connection connection = getConnection();
    	String query = "update users set fullName=?,password=?,phoneNumber=?,age=?,address=? where userId=?";
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1, user.getFullName());
    	statement.setString(2, user.getPassword());
    	statement.setString(3, user.getPhoneNumber());
    	statement.setInt(4, user.getAge());
    	statement.setString(5, user.getAddress());
    	statement.setString(6, userId);
    	if(statement.executeUpdate() > 0) {
    		out.write(gson.toJson("Details updated successfully"));
    	}
    	else {
    		out.write(gson.toJson("Can't updated details"));
    	}
    }
    
    public boolean checkMemberShip(String userId) throws SQLException
    {
    	Connection connection = getConnection();
    	String query = "select membershipEnddate from users where userId = ?";
    	PreparedStatement statement = connection.prepareStatement(query);
    	statement.setString(1, userId);
    	ResultSet rs = statement.executeQuery();
    	
    	if(rs.next())
    	{
    		String str = rs.getString(1);
    		if(str.equals("None"))
    		{
    			return false;
    		}
    		else
    		{
    			LocalDate endDate = LocalDate.parse(str);
    			LocalDate currDate = LocalDate.now();
    			if(endDate.compareTo(currDate) < 0)
    			{
    				query = "update users set membershipEndDate = ? where userId = ?";
    				statement = connection.prepareStatement(query);
    		    	statement.setString(1, "None");
    		    	statement.setString(2,  userId);
    		    	statement.executeUpdate();
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    public void SetSession(String userId) throws SQLException
    {
    	boolean checkMember = checkMemberShip(userId);
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        session.setAttribute("membership", checkMember);
        session.setAttribute("user","user");
    }
    
}


