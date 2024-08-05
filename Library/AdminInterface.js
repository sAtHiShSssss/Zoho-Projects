	
	//switch division in admin page
	function toggleForm(formId)
    {
        var forms = document.querySelectorAll('.form');
        var currentForm = document.getElementById(formId);
        forms.forEach(function(form){
            form.style.display = 'none';
        })
        currentForm.style.display = 'block';
    }
    //toggle side bar
	function toggleSidebar(form1,form2)
	{
        var sidebar = document.getElementById(form1);
        var mainDiv = document.getElementById(form2);
        sidebar.style.width = '20%';
        mainDiv.style.width = '80%';
    }
    //close the side bar
    function closeAddBookPopup(form1,form2)
    {
        var sidebar = document.getElementById(form1);
        var mainDiv = document.getElementById(form2);
        sidebar.style.width = "0";
        mainDiv.style.width = 'auto';
    }

    //add book form generate auto suggestions
    document.getElementById('AddBook').addEventListener('click',function()
    {
        //get authors name
        var authorsNameList = [];
        getAuthorsName();
        function getAuthorsName()
        {
            $.ajax({
                url: 'getAuthorsName',
                method:'GET',
                success: function(response){
                    authorsNameList = response;
                    console.log(authorsNameList);

                },
                error:function(){
                    console.error('Request failed');
                }
            });
        }
        //auto suggestion of authors name
        document.getElementById('authorNameInput').addEventListener('input',function()
        {
            var suggestionsList = document.getElementById('authorSuggestionList');
            var inputValue = this.value.trim().toLowerCase();
            if(inputValue.length > 0)
            {
                suggestionsList.innerHTML = '';

                var filteredSuggestions = authorsNameList.filter(function(suggestion){
                    return suggestion.toLowerCase().startsWith(inputValue);
                });
                filteredSuggestions.forEach(function(suggestion){
                    var listItem = document.createElement('li');
                    listItem.textContent = suggestion;
                    listItem.onclick = function(){
                        document.getElementById('authorNameInput').value = suggestion;
                        suggestionsList.innerHTML = '';
                    };
                    suggestionsList.appendChild(listItem);
                })
            }
            else
            {
                suggestionsList.innerHTML = '';
            }
            document.body.addEventListener('click',function(event){
                suggestionsList.innerHTML = '';
            });
        });

        //get genres name
        var genresList = [];
        getGenresName();
        function getGenresName()
        {
            $.ajax({
                url:'getGenresName',
                method:'GET',
                success:function(response){
                    genresList = response;
                    console.log(genresList);
                },
                error:function(){
                    console.error('Request failed');
                }
            });
        }
        //auto suggestion of genres name
        document.getElementById('genreNameInput').addEventListener('input',function()
        {
            var suggestionsList = document.getElementById('genreSuggestionList');
            var inputValue = this.value.trim().toLowerCase();
            if(inputValue.length > 0)
            {
                suggestionsList.innerHTML = '';
                var filteredSuggestions = genresList.filter(function(genre){
                    return genre.trim().toLowerCase().startsWith(inputValue);
                });
                filteredSuggestions.forEach(function(suggestion){
                    var listItem = document.createElement('li');
                    listItem.textContent = suggestion;
                    listItem.onclick = function(){
                        document.getElementById('genreNameInput').value = suggestion;
                        suggestionsList.innerHTML = '';
                    }
                    suggestionsList.appendChild(listItem);
                });
            }
            else
            {
                suggestionsList.innerHTML = '';
            }
            document.body.addEventListener('click',function(){
                suggestionsList.innerHTML = '';
            });
        });
    });
	//add book and delete book actions using ajax
   	function adminActions(formId)
   	{
   	    document.getElementById('addBookMessage').innerText = '';
   	    document.getElementById('deleteMessage').innerText = '';
		var formData = new FormData(document.getElementById(formId));
		console.log(formData);
		var xhr = new XMLHttpRequest();
		console.log("create");
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					console.log(xhr.responseText);
					var message = xhr.responseText;
					console.log(jsonData);
					if(message === 'bookaddedsuccess'){
						document.getElementById('addBookMessage').innerText = 'Book added successfully';
					}
					else if(message === 'bookaddederror'){
						document.getElementById('addBookMessage').innerText = 'Didn\'t able to add book';
					}
					else if(message === 'deletebooksuccess'){
						document.getElementById('deleteMessage').innerText = 'Book deleted successfully';
					}
					else if(message === 'deletebookerror'){
						document.getElementById('deleteMessage').innerText = 'Didn\'t delete book';
					}
					Reset(formId);
				}
			}
		}
		xhr.onerror = function(){
			console.error('Request Failed');
		}
		
		var url = '';
		if(formId === 'addbook'){
			url = 'addbook.action'
		}
		else if(formId === 'deletebook'){
			url = 'deletebook.action';
		}
		
		xhr.open('post', url, true);
		xhr.send(formData);
		console.log("send");
		
	}
	
	//fetch books from database using AJAX
	fetchBooks();
	document.getElementById('showbooks').addEventListener('click',fetchBooks);
	var booksArray = [];
	function fetchBooks()
	{
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'fetchbooks.action', true);
		xhr.send();
		console.log("send");
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					console.log("books");
					var books = JSON.parse(xhr.responseText);
					booksArray = books;
					displayAllBooks(books);
				}
			}
		}
	}
	//display all books
	function displayAllBooks(books)
	{
		console.log("print");
		var tablebody = document.getElementById('tablebody');
		tablebody.innerHTML = '';
		books.forEach(function(book){
			var row = document.createElement('tr');
			row.innerHTML = '<td>'+book.bookId+'</td>'+'<td>'+book.bookName+'</td>'+'<td>'+book.authorName+'</td>'+'<td>'+book.genreName+'</td>'+'<td>'+book.description+'</td>'+'<td>'+book.publishedDate+'</td>'+'<td>'+book.availableCopies+'</td>'+ '<td>'+book.totalCopies+'</td>'+ '<td>'+book.maxBorrowDays+'</td>'+ '<td>'+book.finePerDay+'</td>';
			tablebody.appendChild(row);
		});
	}
	//filter books by name, auhor, and genre
	function searchBooks()
	{
		console.log("filter");
		var bookName = document.getElementById('booknameinput').value.toLowerCase();
		var authorName = document.getElementById('authornameinput').value.toLowerCase();
		var genreName = document.getElementById('genrenameinput').value.toLowerCase();
		
		var filteredBooks = booksArray.filter(function(book){
			return (book.bookName.toLowerCase().includes(bookName) &&
					 book.authorName.toLowerCase().includes(authorName) &&
					 book.genreName.toLowerCase().includes(genreName));
		});
		displayAllBooks(filteredBooks);
	}
	
	//fetch borrowed books form database using AJAX
	document.getElementById('borrowedbooks').addEventListener('click',fetchBorrowedBooks);
	
	function fetchBorrowedBooks()
	{
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'fetchborrowedbooks.action', true);
		xhr.send();
		console.log("send");
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var books = JSON.parse(xhr.responseText);
					console.log("borrowed");
					displayAllBorrowedBooks(books);
				}
			}
		}
	}
	//display all borrowed books
	function displayAllBorrowedBooks(books)
	{
		var tablebody = document.getElementById('borrowtablebody');
		tablebody.innerHTML = '';
		books.forEach(function(book){
			var row = document.createElement('tr');
			row.innerHTML = '<td>'+book.userId+'</td>'+'<td>'+book.bookId+'</td>'+'<td>'+book.borrowDate+'</td>'+'<td>'+book.lastReturnDate+'</td>'+'<td>'+book.returnDate+'</td>'+'<td>'+'Rs.'+book.fine+'</td>';
			tablebody.appendChild(row);
		});
	}
	

	//Get users from database and show to the admin using AJAX request
	document.getElementById('showallusers').addEventListener('click',fetchUsers);
	
	var usersArray = [];
	function fetchUsers() 
	{
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'showallusers.action', true);
		xhr.send();
		
		xhr.onreadystatechange = function() {
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var users = JSON.parse(xhr.responseText);
					usersArray = users;
					displayAllUsers(users);
				}
			}
		}
	}
	//display all users 
	function displayAllUsers(users) 
	{
		var tablebody = document.getElementById('userstablebody');
		tablebody.innerHTML = '';
		
		users.forEach(function(user) {
			var row = document.createElement('tr');
			row.innerHTML = '<td>' + user.userId + '</td>' + '<td>' + user.fullName + '</td>' + '<td>' + user.email + '<td>' + user.phoneNumber + '</td>' + '<td>' + user.age + '</td>' + '<td>' + user.address + '</td>' + '<td>' + user.membershipEndDate + '</td>';
			tablebody.appendChild(row);
		});
	}
	//search users by their id and name
	function searchUsers() 
	{
		var userIdInput = document.getElementById('useridinput').value.toLowerCase();
		var usernameInput = document.getElementById('nameinput').value.toLowerCase();
		
		var filteredUsers = usersArray.filter(function(user) {
			return  user.userId.toLowerCase().includes(userIdInput) &&
					user.fullName.toLowerCase().includes(usernameInput)
		});
		displayAllUsers(filteredUsers);
	}

    //reset values in form
    function Reset(formId){
        document.getElementById(formId).reset();
    }