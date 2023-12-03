<%@ page language="java" import="java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, task.DatabaseConnection, java.sql.Connection" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Search Results</title>
    <link rel="stylesheet" href="index.css" />
</head>
<body>
   <%! ResultSet searchResults; %>
    <%
        if (session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
        }

        String username = "";
        String searchCriteria = request.getParameter("search");
        ResultSet searchResults = null; // Move the declaration outside the try block

        try {
            Connection con = DatabaseConnection.getConnection();
            String query1 = "SELECT username FROM user WHERE id=?";
            String query2 = "SELECT * FROM task WHERE userId=? AND title LIKE ?";

            String id = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("id")) id = cookie.getValue();
                }
            }

            PreparedStatement ps1 = con.prepareStatement(query1);
            PreparedStatement ps2 = con.prepareStatement(query2);

            ps1.setString(1, id);
            ps2.setString(1, id);
            ps2.setString(2, "%" + searchCriteria + "%");

            ResultSet userResult = ps1.executeQuery();
            searchResults = ps2.executeQuery(); 

            if (!userResult.next()) {
                response.sendRedirect("login.jsp");
            } else {
                username = userResult.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp");
        }
    %>

    <h1>Welcome <%= username %></h1>
    <h2>Search Results for "<%= searchCriteria %>"</h2>

    <% 
        try {
            while (searchResults != null && searchResults.next()) {
                String taskId = searchResults.getString("id");
                String updateLink = "update.jsp?id=" + taskId;
                String deleteLink = "delete?id=" + taskId;
    %>
                <div class="outerContainer">
			<div class="container">
    		<div class="innerContainer">
	    		<div class="TD">
		    		<h2><%= searchResults.getString("title")%></h2>
		    		<p><%= searchResults.getString("due_date")%><p>
	    		</div>
	    		<div class="updates">
				     <h2>
	                    <input class="checkbox" type="checkbox" id="completed_<%=  taskId %>" value="<%= taskId %>" <% if (searchResults.getInt("completed") == 1) out.print("checked"); %> />

	                    <label for="completed_<%= taskId %>"><%= searchResults.getString("title") %></label>
	                </h2>
				    <div>
				        <a href="<%= updateLink %>">Edit</a>
				        <a href="<%= deleteLink %>">Delete</a>
				    </div>
				</div>
    		</div>
	    	<p><%= searchResults.getString("description")%></p>
    	</div>
		</div>
    <% 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>

    <a href="create.jsp">Add task</a>
</body>
</html>
