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
	<%! ResultSet userResults; %>
	<%
		if(session.getAttribute("id") == null){
			response.sendRedirect("login.jsp");
		}
		
		try {
			Connection con = DatabaseConnection.getConnection();
			String query = "Select username from user where id=?";
			
			String id = null;
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("id")) id = cookie.getValue();
				}
			}
			
			if (id == null) response.sendRedirect("login.jsp");
			
			PreparedStatement ps1 = con.prepareStatement(query);
			
			ps1.setString(1, id);
			
			userResults = ps1.executeQuery();
			if (!userResults.next()) response.sendRedirect("login.jsp");
				
		}catch (SQLException se) {
			se.printStackTrace();
			response.sendRedirect("login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login.jsp");
		}
	%>
	<form action="updateprofile">
		<div>
			<label for="username">UserName: </label>
			<input type="text" id="username" name="username" value=<%= userResults.getString("username") %> />
		</div>
		<div>
			<label for="password">Password: </label>
			<input type="password" id="password" name="password" />
		</div>
		<button>Edit</button>
	</form>
</body>
</html>