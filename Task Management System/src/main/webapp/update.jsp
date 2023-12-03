<%@ page language="java" import="java.sql.PreparedStatement" import="java.sql.ResultSet" import="java.sql.SQLException" import="task.DatabaseConnection" import="java.sql.Connection" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="index.css" />
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
			String query = "Select * from task where id=?";
			
			String id = request.getParameter("id");
			System.out.println(id);
			if (id == null) response.sendRedirect("dashboard.jsp");
			
			PreparedStatement ps1 =con.prepareStatement(query);
			
			ps1.setString(1, id);
			
			taskResults = ps1.executeQuery();
			if (!taskResults.next()) response.sendRedirect("dashboard.jsp");
				
		}catch (SQLException se) {
			se.printStackTrace();
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.jsp");
		}
	%>
	<form action="update">
		<div>
			<label for="title">Title: </label>
			<input type="text" id="title" name="title" value=<%= taskResults.getString("title") %> />
		</div>
		<div>
			<label for="description">Description: </label>
			<input type="text" id="description" name="description" value=<%= taskResults.getString("description") %> />
		</div>
		<div>
		    <label for="due_date">Due Date: </label>
		    <input type="text" id="due_date" name="due_date" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(taskResults.getDate("due_date")) %>"/>
		</div>

		<div>
			<label for="priority_level">Priority Level:</label>

			<select name="priority_level" id="priority_level">
				<option value="high">High</option>
				<option value="medium">Medium</option>
				<option value="low" selected>Low</option>
			</select>		
		</div>
		<input type="hidden" value="<%= taskResults.getString("id")%>"  name="id"/>
		<button>Update</button>
	</form>

</body>
</html>