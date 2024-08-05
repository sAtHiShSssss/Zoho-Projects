
	
	//switch division in user page
	function toggleForm(formId)
    {
        var forms = document.querySelectorAll('.form');
        forms.forEach(function(form) {
            form.style.display = 'none';
        });
        document.getElementById(formId).style.display = 'block';
    }
    //toggle side bar
    function toggleSidebar(form1,form2)
    {
        var sidebar = document.getElementById(form1);
        var mainDiv = document.getElementById(form2);
        sidebar.style.width = '20%';
        mainDiv.style.width = '80%';
    }
    //close side bar
    function closeAddBookPopup(form1,form2)
    {
        var sidebar = document.getElementById(form1);
        var mainDiv = document.getElementById(form2);
        sidebar.style.width = "0";
        mainDiv.style.width = 'auto';
    }

    //Show user borrowed books
    fetchUserBooks();
    document.getElementById('showmybooks').addEventListener('click',fetchUserBooks);
    var borrowBooks = [];
    function fetchUserBooks()
    {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'userborrowbooks', true);
        xhr.send();

        xhr.onreadystatechange = function(){
            if(xhr.readyState === 4){
                if(xhr.status === 200){
                    console.log(xhr.responseText);
                    var books = JSON.parse(xhr.responseText);
                    borrowBooks = books;
                    displayUsersBooks();
                }
            }
        }
    }
    //display all books
    function displayUsersBooks()
    {
        var tablebody = document.getElementById('myborrowbooks');
        tablebody.innerHTML = '';
        borrowBooks.forEach(function(book){
            var row = document.createElement('tr');
            row.innerHTML = '<td>'+book.bookName+'</td>'+'<td>'+book.borrowDate+'</td>'+'<td>'+book.dueDate+'</td>'+'<td>'+book.returnDate+'</td>'+'<td>'+'Rs.'+book.fine+'</td>';
            tablebody.appendChild(row);
        });
    }

	//fetch books from database using AJAX
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
	
	//borrow book using ajax
	function borrowBook(formId)
	{
		var message = document.getElementById('borrowbookmessage');
		message.innerText = '';
		
		var formData = new FormData(document.getElementById(formId));
		console.log(formData);
		
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'borrowbook.action', true);
		xhr.send(formData);
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					message.innerHTML = xhr.responseText;
					Reset(formId);
				}
			}
		}
		xhr.onerror = function(){
			console.log('Request failed');
		}
	}
	
	//Renew book using ajax
	function renewBook(formId)
	{
		var message = document.getElementById('renewbookmessage');
		message.innerText = '';
		
		var formData = new FormData(document.getElementById(formId));
		console.log(formData);
		
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'renewbook.action', true);
		xhr.send(formData);
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var jsonData = JSON.parse(xhr.responseText);
					message.innerText = jsonData;
				}
				Reset(formId);
			}
		}
		xhr.onerror = function(){
			console.log('Request failed');
		}
	}
	
	//Return book using ajax
	function returnBook(formId)
	{
		var message = document.getElementById('returnBookMessage');
		message.innerText = '';
		
		var formData = new FormData(document.getElementById(formId));
		console.log(formData);
		
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'returnbook.action', true);
		xhr.send(formData);
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var jsonData = JSON.parse(xhr.responseText);
					message.innerText = jsonData;
				}
				Reset(formId);
			}
		}
		xhr.onerror = function(){
			console.log('Request failed');
		}
	}
	

	
	
	//Subscribe membership for user
	function subscribeMembership()
	{
		var messageBox = document.getElementById('membershipMessage');
		messageBox.innerText = '';
		
		var xhr = new XMLHttpRequest();
		xhr.open('post', 'subscribeMembership.action', true);
		xhr.send();
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var json = JSON.parse(xhr.responseText);
					
					if(json === 'Already member'){
						messageBox.innerText = 'You have already membership';
					}
					else if(json === 'success'){
						messageBox.innerText = 'Membership subscribed successfully';
					}
					else{
						messageBox.innerText = 'Can\'t subscribe membership';
					}
				}
			}
		}
		xhr.onerror = function(){
			console.log('Request failed');
		}
		
	}
	
	// fetch user details and show to user
	document.getElementById('userdetails').addEventListener('click',fetchUserDetails);
	function fetchUserDetails()
	{
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'fetchdetails.action', true);
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var jsonData = JSON.parse(xhr.responseText);
					for(var key in jsonData){
						if(jsonData.hasOwnProperty(key)){
							var element = document.getElementsByName(key)
							element[0].value = jsonData[key];
							element[0].disabled = true;
						}
					}
					
				}
			}
		}
		xhr.send();
	}
	
	document.getElementById('MyDetails').addEventListener('click',function(){
		document.getElementById('updatemessage').innerText = '';
	});
	
	document.getElementById('edit').addEventListener('click', function(){
		var inputFields = document.querySelectorAll('#MyDetails input');
		for(var i=1;i<inputFields.length-1;i++){
			inputFields[i].disabled = false;
			if(i == 2){
				inputFields[i].disabled = true;
			}
		}
		document.getElementById('edit').style.display = 'none';
		document.getElementById('save').style.display = 'block';
	});
	
	
	function updateDetails(formId)
	{
		var updateMessage = document.getElementById('updatemessage');
		updateMessage.innerText = ' ';
		
		var formData = new FormData(document.getElementById(formId));
		var xhr = new XMLHttpRequest();
		xhr.open('POST', 'updatedetails.action', true);
		xhr.send(formData);
		
		xhr.onreadystatechange = function(){
			if(xhr.readyState === 4){
				if(xhr.status === 200){
					var message = JSON.parse(xhr.responseText);
					updateMessage.innerText = message;
				}
			}
		}
		xhr.onerror = function(){
			console.error("Request failed");
		}
	}
	
	//reset values in form
    function Reset(formId){
        document.getElementById(formId).reset();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	