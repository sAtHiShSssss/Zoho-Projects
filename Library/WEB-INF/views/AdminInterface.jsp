<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Library Management</title>
<style>
	    .form{
            display:none;
        }
        .menu {
            position: absolute;
            top: 0;
            right: 100px;
            margin: 20px;
        }
        .logout{
            position: absolute;
            top:50px;
            right:50px;
        }
        h1, .menu button{
            margin-top: 30px;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            z-index: 999;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .menu:hover .dropdown-content {
            display: block;
        }

        table{
            border-collapse:collapse;
            width:100%;
            border: 1px solid #ddd;
        }
        th{
            background-color: #f2f2f2;
            padding: 8px;
            text-align: left;
            border: 1px solid #ddd;
        }
        td{
            padding: 8px;
            border: 1px solid #ddd;
        }
        #showallbooks div, #showallauthors div, #showallgenres div{
            position: absolute;
            top: 200px;
            right:40px;
        }
        .sidebar {
            height: 100%;
            width: 0;
            position:fixed;
            top: 0;
            right: 0;
            background-color: #f0f0f0;
            overflow-x: hidden;
            padding-top: 60px;
            display: block;
        }

        .closeButton {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }
        #showallbooks{
            display: block;
        }
        .suggestionsList {
            list-style-type: none;
            padding: 0;
            margin: 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            max-height: 200px;
            overflow-y: auto;
            width:170px;
        }
        .suggestionsList li {
            padding: 10px;
            cursor: pointer;
            background-color:azure;
            transition: background-color 0.3s ease;
        }
        .suggestionsList li:hover {
            background-color: #f2f2f2;
        }

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<%
	    if(session.getAttribute("admin") == null){
            response.sendRedirect("login");
        }
	%>
    <h1>Library Management</h1>

    <div class="menu">
        <button>Menu</button>
        <div class="dropdown-content">
            <a id="showbooks" href="#" onclick="toggleForm('showallbooks')">Show Books</a>
            <a id="showauthors" href="#" onclick="toggleForm('showallauthors')">Show Authors</a>
            <a id="showgenres" href="#" onclick="toggleForm('showallgenres')">Show Genres</a>
            <a id="borrowedbooks" href="#" onclick="toggleForm('showborrowedbooks')">Show Borrowed Books</a>
            <a id="showusers" href="#" onclick="toggleForm('showallusers')">Show All Users</a>
        </div>
    </div>

    <div class="logout">
    	<form action="login">
    		<input type="submit" value="Logout">
    	</form>
    </div>

    <div class="form" id="showallbooks">
        <h2>All Books</h2>
        <p>Search by...</p>
        <input type="text" id="booknameinput" name="bookname" placeholder="Enter book name..." onkeyup="searchBooks()">
        <input type="text" id="authornameinput" name="authorname" placeholder="Enter author name..." onkeyup="searchBooks()">
        <input type="text" id="genrenameinput" name="genrename" placeholder="Enter genre name..." onkeyup="searchBooks()">
            <div>
                <input id="AddBook" type="submit" value="Add Book" onclick="toggleSidebar('addBookForm','showallbooks')">
                <input id="EditBook" type="submit" value="Edit Book" onclick="toggleSidebar('editBookForm','showallbooks')">
                <input id="DeleteBook" type="submit" value="Delete Book" onclick="toggleSidebar('deleteBookForm','showallbooks')">
            </div>
            <br><br>
            <table id="booktable">
                <thead>
                    <tr>
                        <th>Book ID</th> <th>Book Name</th> <th>Author Name</th> <th>Genre Name</th> <th>Description</th> <th>Published Date</th> <th>Available Copies</th> <th>Total Copies</th> <th>Max Borrow Days</th> <th>Fine Per Day</th>
                    </tr>
                </thead>

                <tbody id="tablebody">

                </tbody>
            </table>
    </div>

            <div class="sidebar" id="addBookForm" >
                <h2>Add Book</h2>
                <button class="closeButton" onclick="closeAddBookPopup('addBookForm','showallbooks')">X</button>
                <form id="addbook" onsubmit=" adminActions('addbook'); return false;">
                    Enter book name<br><input id="bookNameInput" type="text" name="bookName" required><br>
                    Enter author name<br><input id="authorNameInput" type="text" name="authorName" required><br>
                    <ul class="suggestionsList" id="authorSuggestionList"></ul>
                    Enter genre name<br><input id="genreNameInput" type="text" name="genreName" required><br>
                    <ul class="suggestionsList" id="genreSuggestionList"></ul>
                    Enter brief description<br><textarea id="descriptionInput" name="description" rows=3 cols=20></textarea><br>
                    Enter published date<br><input id="publishedDateInput" type="date" name="publishedDate" required><br>
                    Enter total copies<br><input id="totalCopiesInput" type="number" name="totalCopies" required><br>
                    Enter maximum borrow days<br><input id="maxBorrowDaysInput" type="number" name="maxBorrowDays" required><br>
                    Enter fine per day<br><input id="finePerDayInput" type="number" name="finePerDay" required><br><br>
                    <input type="submit" value="Add book"><br>
                </form>
                <p id="addBookMessage">Book added successfully</p>
            </div>

            <div class="sidebar" id="editBookForm">
                <h2>Edit Book</h2>
                <button class="closeButton" onclick="closeAddBookPopup('editBookForm','showallbooks')">X</button>
                <form id="editbook" onsubmit="">
                </form>
            </div>

            <div class="sidebar" id="deleteBookForm">
                <h2>Delete Book</h2>
                <button class="closeButton" onclick="closeAddBookPopup('deleteBookForm','showallbooks')">X</button>
                <form id="deletebook" onsubmit=" adminActions('deletebook'); return false;">
                    Enter book id to delete<br><input type="text" name="bookId" required><br><br>
                    <input type="submit" value="Delete Book">
                </form>
                <p id="deleteMessage">Book deleted successfully</p>
            </div>

    <div class="form" id="showallauthors">
        <h2>All Authors</h2>
        <p>Search by...</p>
        <input type="text" id="authornameinput" name="authorname" placeholder="Enter author name..." onkeyup="searchAuthors()">
        <div>
            <input type="submit" value="Add Author" onclick="toggleSidebar('addAuthorForm','showallauthors')">
            <input type="submit" value="Edit Author" onclick="toggleSidebar('editAuthorForm','showallauthors')">
            <input type="submit" value="Delete Author" onclick="toggleSidebar('deleteAuthorForm','showallauthors')">
        </div>
        <br><br>
        <table id="authortable">
            <thead>
                <tr>
                    <th>Author ID</th> <th>Author Name</th> <th>Author Contact</th> <th>Author Email</th>
                </tr>
            </thead>

            <tbody id="authortablebody">

            </tbody>
        </table>
    </div>

            <div class="sidebar" id="addAuthorForm">
                <h2>Add Author</h2>
                <button class="closeButton" onclick="closeAddBookPopup('addAuthorForm','showallauthors')">X</button>
                <form id="addauthor" onsubmit=" adminActions('addauthor'); return false;">
                    Enter book id to delete<br><input type="text" name="bookId" required><br><br>
                    <input type="submit" value="Delete Book">
                </form>
                <p id="deleteMessage">Book deleted successfully</p>
            </div>

            <div class="sidebar" id="editAuthorForm">
                <h2>Edit Author</h2>
                <button class="closeButton" onclick="closeAddBookPopup('editAuthorForm','showallauthors')">X</button>
            </div>

            <div class="sidebar" id="deleteAuthorForm">
                <h2>Delete Author</h2>
                <button class="closeButton" onclick="closeAddBookPopup('deleteAuthorForm','showallauthors')">X</button>
                <form id="deleteauthor" onsubmit=" adminActions('deleteauthor'); return false;">
                    Enter book id to delete<br><input type="text" name="bookId" required><br><br>
                    <input type="submit" value="Delete Book">
                </form>
                <p id="deleteMessage">Book deleted successfully</p>
            </div>

    <div class="form" id="showallgenres">
        <h2>All Genres</h2>
        <p>Search by...</p>
        <input type="text" id="genrenameinput" name="genreName" placeholder="Enter genre name..." onkeyup="searchGenres()">
        <div>
            <input type="submit" value="Add Author" onclick="toggleSidebar('addGenreForm','showallgenres')">
            <input type="submit" value="Edit Author" onclick="toggleSidebar('editGenreForm','showallgenres')">
            <input type="submit" value="Delete Author" onclick="toggleSidebar('deleteGenreForm','showallgenres')">
        </div>
        <br><br>
        <table id="genretable">
            <thead>
                <tr>
                    <th>Genre Id</th> <th>Genre Name</th> <th>Description</th>
                </tr>
            </thead>

            <tbody id="genretablebody">

            </tbody>
        </table>
    </div>

            <div class="sidebar" id="addGenreForm">
                <h2>Add Genre</h2>
                <button class="closeButton" onclick="closeAddBookPopup('addGenreForm','showallgenres')">X</button>
                <form id="addgenre" onsubmit=" adminActions('addgenre'); return false;">
                    Enter book id to delete<br><input type="text" name="bookId" required><br><br>
                    <input type="submit" value="Delete Book">
                </form>
                <p id="deleteMessage">Genre added successfully</p>
            </div>

            <div class="sidebar" id="editGenreForm">
                <h2>Edit Author</h2>
                <button class="closeButton" onclick="closeAddBookPopup('editGenreForm','showallgenres')">X</button>
            </div>

            <div class="sidebar" id="deleteGenreForm">
                <h2>Delete Author</h2>
                <button class="closeButton" onclick="closeAddBookPopup('deleteGenreForm','showallgenres')">X</button>
                <form id="deletegenre" onsubmit=" adminActions('deletegenre'); return false;">
                    Enter book id to delete<br><input type="text" name="bookId" required><br><br>
                    <input type="submit" value="Delete Book">
                </form>
                <p id="deleteMessage">Genre deleted successfully</p>
            </div>

    <div class="form" id="showborrowedbooks">
        <br><h3>Borrowed Books</h3>
        <br>
        <table id="borrowbooktable">
            <thead>
                <tr>
                    <th>Book Name</th> <th>User Id</th> <th>User Name</th> <th>Borrow Date</th> <th>Due Date</th> <th>Return Date</th> <th>Fine</th>
                </tr>
            </thead>

            <tbody id="borrowtablebody">

            </tbody>
        </table>
    </div>

    <div class="form" id="showallusers">
        <h2>All Users</h2>
        <p>Search by...</p>
        <input type="text" id="useridinput" name="userid" placeholder="Enter user id..." onkeyup="searchUsers()">
        <input type="text" id="nameinput" name="fullname" placeholder="Enter name..." onkeyup="searchUsers()"><br><br>

        <table id="usertable">
            <thead>
                <tr>
                    <th>User Id</th> <th>Full Name</th> <th>Email</th>  <th>Phone Number</th> <th>Age</th> <th>Address</th> <th>Membership End Date</th>
                </tr>
            </thead>

            <tbody id="userstablebody">

            </tbody>
        </table>
    </div>

    <script src="AdminInterface.js"></script>

</body>
</html>

