<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

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
            position: fixed;
            top:50px;
            right:50px;
        }

        h1, .menu button{
            margin-top: 30px;
            display: inline-block;
        }

        .dropdown-content {
            z-index: 9999;
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
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
        #MyBooks{
            display:block;
        }
        #MyBooks h3,a{
            display:inline-block;
        }
        #MyBooks a{
            left:300px;
        }
        #MyBooks div{
            position: absolute;
            top: 120px;
            right:40px;
        }
        #ShowAllBooks div{
            position: absolute;
            top: 190px;
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
	</style>
</head>
<body>

	<%
	    if(session.getAttribute("user") == null){
	        response.sendRedirect("login");
	    }
	%>

    <h1>Library Management</h1>
    
    <div class="menu">
            <button>Menu</button>
            <div class="dropdown-content">
                <a id="showmybooks" href="#" onclick="toggleForm('MyBooks')">My Books</a>
                <a id="showbooks" href="#" onclick="toggleForm('ShowAllBooks')">Show Books</a>
                <a id="subscribemembership" href="#" onclick="toggleForm('SubscribeMembership')">Subscribe Membership</a>
                <a id="userdetails" href="#" onclick="toggleForm('MyDetails')">My Profile</a>
            </div>
        </div>

    <div class="logout">
        <form action=login>
        	<input type="submit" value="Logout">
        </form>
    </div>

    <div class="form" id="MyBooks">
    		<h3>My Books</h3>
            <div>
                <input type="submit" value="Renew Book" onclick="toggleSidebar('RenewBook','MyBooks')">
                <input type="submit" value="Return Book" onclick="toggleSidebar('ReturnBook','MyBooks')">
            </div>
    		<table id="usertable">
                <thead>
                    <tr>
                        <th>Book Name</th> <th>Borrow Date</th> <th>Due Date</th> <th>Return Date</th> <th>Fine</th>
                    </tr>
                </thead>

                <tbody id="myborrowbooks">
                </tbody>
            </table>
    	</div>

        <div class="sidebar" id="RenewBook">
            <h3>Renew Book</h3>
            <button class="closeButton" onclick="closeAddBookPopup('RenewBook','MyBooks')">X</button>
                <form id="renewbook" onsubmit="renewBook('renewbook'); return false;">
                    Enter Book ID<br><input type="text" name="bookID" required><br><br>
                    <input type="submit" value="submit">
                </form>
                <p id="renewbookmessage"></p>
        </div>

        <div class="sidebar" id="ReturnBook">
            <h3>Return Book</h3>
            <button class="closeButton" onclick="closeAddBookPopup('ReturnBook','MyBooks')">X</button>
            <form id="returnbook" onsubmit="returnBook('returnbook'); return false;">
                Enter Book ID<br><input type="text" name="bookId" required><br><br>
                <input type="submit" value="submit">
            </form>
            <p id="returnBookMessage"></p>
        </div>


        <div class="form" id="ShowAllBooks">
    		<h3>All Books</h3>
            <p>Search by...</p>
            <input type="text" id="booknameinput" name="bookname" placeholder="Enter book name..." onkeyup="searchBooks()">
            <input type="text" id="authornameinput" name="authorname" placeholder="Enter author name..." onkeyup="searchBooks()">
            <input type="text" id="genrenameinput" name="genrename" placeholder="Enter genre name..." onkeyup="searchBooks()">
            <div>
                <input type="submit" value="Borrow Book" onclick="toggleSidebar('BorrowBook','ShowAllBooks')">
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

    	<div class="sidebar" id="BorrowBook">
    	    <h3>Borrow Book</h3>
            <button class="closeButton" onclick="closeAddBookPopup('BorrowBook','ShowAllBooks')">X</button>
            <form id="borrow" onsubmit=" borrowBook('borrow'); return false; ">
                Enter Book ID<br><input type="text" name="bookId" required><br><br>
                <input type="submit" value="Submit">
            </form>
            <p id="borrowbookmessage"></p>
        </div>

        <div class="form" id="SubscribeMembership">
            <h3>Subscribe Membership</h3>
            <form id="subscribemembership" onsubmit="subscribeMembership(); return false;">
            	<p> The monthly membership price is Rs.200<br>
            		Do you want subscribe membership click the below button </p>
            	<input type="submit" value="Subscibe">
            </form>
            <p id="membershipMessage" ></p>
        </div>

        <div class="form" id="MyDetails">
            <h3>Update My Details</h3>
            <form id="editdetails" onsubmit="updateDetails('editdetails'); return false;">
            	User Id<br><input type="text" id="userid" name="userId"><br>
            	Full Name<br><input type="text" id="userfullname" name="fullName"><br>
            	Email<br><input type="email" id="useremail" name="email"><br>
            	Password<br><input type="text" id="userpassword" name="password"><br>
            	Phone Number<br><input type="text" id="userphoneNumber" name="phoneNumber"><br>
            	Age<br><input type="number" id="userage" name="age"><br>
            	Address<br><input type="text" id="useraddress" name="address"><br>
            	Membership End Date<br><input type="text" id="usermembershipEndDate" name="membershipEndDate"><br>
            	<input id="save" type="submit" value="Save" style="display:none">
            </form>
            <button id="edit">Edit</button>
            <p id="updatemessage"></p>
        </div>

	<script src="UserInterface.js"></script>


</body>
</html>