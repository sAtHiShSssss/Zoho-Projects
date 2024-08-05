package com.sathish.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.sathish.model.User;
import org.apache.struts2.ServletActionContext;
import com.sathish.model.Book;
import com.sathish.model.BorrowedBook;
import com.sathish.model.DBUtils;

public class AdminService
{
    public Connection getConnection()
    {
        try
        {
            //connection to database
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
    public boolean addBook(Book book)
    {
        Connection connection = getConnection();
        try
        {
            //insert new book in the books table
            String authorId = getAuthorId(connection, book.getAuthorName());
            String genreId = getGenreId(connection, book.getGenreName());
            System.out.println(authorId+"   "+genreId);
            String query = "insert into Books(bookName,authorId,genreId,description,publishedDate,availableCopies,totalCopies,maxBorrowDays,finePerDay) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getBookName());
            statement.setString(2, authorId);
            statement.setString(3,  genreId);
            statement.setString(4,  book.getDescription());
            statement.setDate(5,  Date.valueOf(book.getPublishedDate()));
            statement.setInt(6,  book.getTotalCopies());
            statement.setInt(7,  book.getTotalCopies());
            statement.setInt(8,  book.getMaxBorrowDays());
            statement.setInt(9,  book.getFinePerDay());

            if(statement.executeUpdate() > 0)
            {
                return true;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public String getAuthorId(Connection connection, String authorName) throws SQLException
    {
        //get author id from author table
        String query = "select * from Authors where authorName = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, authorName);
        ResultSet rs = statement.executeQuery();

        if(rs.next())
        {
            // if author already existed return his id
            return rs.getString(1);
        }
        else
        {
            //insert new author in author table
            query = "insert into Authors(authorName) values(?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, authorName);
            if(statement.executeUpdate() > 0)
            {
                System.out.println("Author Inserted Sucessfully...");
            }

            //Get new inserted author id and return
            query = "select * from Authors where authorName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, authorName);
            rs = statement.executeQuery();
            while(rs.next())
            {
                return rs.getString(1);
            }
        }
        return "";
    }
    public String getGenreId(Connection connection, String genreName)throws SQLException
    {
        //get genre id if already exists
        String query = "select * from Genres where genreName = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, genreName);
        ResultSet rs = statement.executeQuery();

        if(rs.next())
        {
            return rs.getString(1);
        }
        //if genre didn't exist insert the new genre and get his id
        else
        {
            query = "insert into Genres(genreName) values(?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, genreName);
            if(statement.executeUpdate() > 0)
            {
                System.out.println("Genre Inserted Sucessfully...");
            }

            query = "select * from Genres where genreName = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, genreName);
            rs = statement.executeQuery();
            while(rs.next())
            {
                return rs.getString(1);
            }
        }
        return "";
    }

    public boolean deleteBook(String bookId) throws SQLException 
    {
        //delete book from books table
        Connection connection = getConnection();
        String query = "delete from books where bookId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,  bookId);
        if(statement.executeUpdate() > 0)
        {
            return true;
        }
        return false;
    }

    public String fetchAllBooks() throws SQLException
    {
        //get all books from books table
        List<Book> books = new ArrayList<>();
        Connection connection = getConnection();
        String query = "select b.bookId,b.bookName,a.authorName,g.genreName,b.description,b.publishedDate,b.availableCopies,b.totalCopies,b.maxBorrowDays,b.finePerDay from Books b inner join Authors a on b.authorId=a.authorId inner join Genres g on b.genreId=g.genreId";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        while(rs.next())
        {
            books.add(new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10)));
        }
        HttpServletResponse res = ServletActionContext.getResponse();
        Gson gson = new Gson();
        return gson.toJson(books);
    }
    
    public String fetchBorrowedBooks() throws SQLException
    {
        //get all user borrowed books
    	List<BorrowedBook> borrowedBooks = new ArrayList<>();
    	Connection connection = getConnection();
    	String query = "select * from borrowedBooks order by case when returnDate is not null then returnDate else dueDate end desc";
    	PreparedStatement statement = connection.prepareStatement(query);
    	ResultSet rs = statement.executeQuery();
    	while(rs.next())
    	{
    		borrowedBooks.add(new BorrowedBook(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6)));
    	}
    	Gson gson = new Gson();
    	return gson.toJson(borrowedBooks);
    }

    public String fetchUsers() throws SQLException
    {
        //get all users
        List<User> users = new ArrayList<>();
        Connection connection = getConnection();
        String query = "select * from users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next())
        {
            users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8)));
        }
        Gson gson = new Gson();
        String jsonData = gson.toJson(users);
        return jsonData;
    }
    public void getAuthorsName()
    {
        Connection connection = getConnection();
        try
        {
            String query = "select authorName from authors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while(rs.next())
            {
                names.add(rs.getString(1));
            }
            HttpServletResponse response = ServletActionContext.getResponse();
            PrintWriter out = response.getWriter();
            out.write(new Gson().toJson(names));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void getGenresName()
    {
        Connection connection = getConnection();
        try
        {
            String query = "select genreName from genres";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            List<String> names = new ArrayList<>();
            while(rs.next())
            {
                names.add(rs.getString(1));
            }
            HttpServletResponse response = ServletActionContext.getResponse();
            PrintWriter out = response.getWriter();
            out.write(new Gson().toJson(names));
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
