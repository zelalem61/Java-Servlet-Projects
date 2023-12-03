package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
@Override
protected void doGet(

	HttpServletRequest req,
	HttpServletResponse resp) throws IOException{
		
	try {
		Connection conn = DatabaseConnection.getConnection();
		
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		PreparedStatement ps = conn.prepareStatement(query);
		
		ps.setString(1, name);
		ps.setString(2, password);
		ps.setString(3, email);
		
		int count = ps.executeUpdate();
		
		if(count == 1) {
		  resp.sendRedirect("Confirmation.jsp");
		}
		else {
		    resp.sendRedirect("Registration.jsp");
		}
		} catch (SQLException se) {
			se.printStackTrace();
			resp.sendRedirect("Registration.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("Registration.jsp");
		}
}

	private static final String query =
	"insert into user(name, password, email) values(?, ?, ?)";
	@Override
	
	protected void doPost(

	HttpServletRequest req,
	HttpServletResponse resp) throws IOException {
	
	doGet(req, resp);
}
}
