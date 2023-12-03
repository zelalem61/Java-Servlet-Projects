<%@ page language="java" import="java.sql.PreparedStatement" import="java.sql.ResultSet" import="java.sql.SQLException" import="task.DatabaseConnection" import="java.sql.Connection" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="index.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        input[type="checkbox"] {
            margin-right: 5px;
        }

        .completed-task {
            text-decoration: line-through;
            color: #555;
        }
    </style>
    <script>
    document.addEventListener('DOMContentLoaded', function () {
        const checkboxes = document.querySelectorAll('input[type=checkbox]');
        checkboxes.forEach((checkbox) => {
            checkbox.addEventListener('change', function () {
                const taskId = this.id.split("_")[1]; 
                const taskTitle = this.nextElementSibling;

                if (this.checked) {
                    taskTitle.classList.add('completed-task'); 
                } else {
                    taskTitle.classList.remove('completed-task'); 
                }

                
                console.log("Task ID: " + taskId);
                updateDatabase(taskId, this.checked);
            });
        });

        function updateDatabase(taskId, isCompleted) {
        	fetch('updateTaskStatus', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: "taskId=" + taskId + "&isCompleted=" + isCompleted,
            })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        }
    });
</script>
</head>
<body>
	<%! ResultSet taskResults; %>
	<%
		if(session.getAttribute("id") == null){
			response.sendRedirect("login.jsp");
		}
		String username = "";
		
		try {
			Connection con = DatabaseConnection.getConnection();
			String query1 = "Select username from user where id=?";
			String query2 = "Select * from task where userId=?";
			
			String id = null;
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("id")) id = cookie.getValue();
				}
			}

			
			PreparedStatement ps1 =con.prepareStatement(query1);
			PreparedStatement ps2 =con.prepareStatement(query2);
			
			ps1.setString(1, id);
			ps2.setString(1, id);
			
			ResultSet userResult = ps1.executeQuery();
			taskResults = ps2.executeQuery();
			
			if (!userResult.next()) response.sendRedirect("login.jsp");
			else username = userResult.getString("username");
			
		}catch (SQLException se) {
			se.printStackTrace();
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.jsp");
		}
	%>
	<h1>Welcome <%= username %></h1>
	<a href="updateprofile.jsp">
        <i class="fas fa-user">Edit</i>
    </a>
	 <form class="search" method="post" action="search.jsp">
        <label for="search">Search: </label>
        <input type="text" id="search" name="search">
        <input type="submit" value="Search">
    </form>
	<% while (taskResults.next()) {
		String id = taskResults.getString("id");
		String updateLink = "update.jsp?id=" + taskResults.getString("id");
		String delteLink = "delete?id=" + taskResults.getString("id");
		boolean isCompleted = taskResults.getBoolean("completed");
		
	
	%>
		<div class="outerContainer">
			<div class="container">
    		<div class="innerContainer">
	    		<div class="TD">
		    		<h2><%= taskResults.getString("title")%></h2>
		    		<p><%= taskResults.getString("due_date")%><p>
	    		</div>
	    		<div class="updates">
				     <h2>
	                    <input class="checkbox" type="checkbox" id="completed_<%=  id %>" value="<%= id  %>" <% if (taskResults.getInt("completed") == 1) out.print("checked"); %> />

	                    <label for="completed_<%= id %>"><%= taskResults.getString("title") %></label>
	                </h2>
				    <div>
				        <a href="<%= updateLink %>">Edit</a>
				        <a href="<%= delteLink %>">Delete</a>
				    </div>
				</div>
    		</div>
	    	<p><%= taskResults.getString("description")%></p>
    	</div>
		</div>
    	
	<% } %>
	<a href="create.jsp">Add task</a>
	
</body>
</html>