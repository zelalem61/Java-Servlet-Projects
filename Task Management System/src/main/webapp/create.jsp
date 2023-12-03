<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="index.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%! String id; %>
	<%
		if(session.getAttribute("id") == null){
			response.sendRedirect("login.jsp");
		}
		id = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("id")) id = cookie.getValue();
			}
		}

	%>
	<form action="create">
		<div>
			<label for="title">Title: </label>
			<input type="text" id="title" name="title" />
		</div>
		<div>
			<label for="description">Description: </label>
			<input type="text" id="description" name="description" />
		</div>
		<div class="date">
			<label for="due_date">Due Date: </label>
			<input type="date" id="due_date" name="due_date" />
		</div>
		<div>
			<label for="priority_level">Priority Level:</label>

			<select  name="priority_level" id="priority_level" >
				<option value="high">High</option>
				<option value="medium">Medium</option>
				<option value="low" selected>Low</option>
			</select>		
		</div>
		<input type="hidden" value=<%= id %> name="userId"/>
		<button>Create</button>
	</form>

</body>
</html>