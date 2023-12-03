package com.itsc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Connection conn = DatabaseConnection.getConnection();
			
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, email);
			
			ResultSet result = ps.executeQuery();
			
			if(result.next()) {
				if (password.equals(result.getString("password"))) {	
					HttpSession session = req.getSession();
					session.setAttribute("exist", true);
					//setting session to expiry in 30 mins
					session.setMaxInactiveInterval(30*60);
					System.out.println(result.getString("password"));
					Cookie firstname = new Cookie("firstname", result.getString("name").split(" ")[0]);
					Cookie lastname = new Cookie("lastname", result.getString("name").split(" ")[1]);
					firstname.setMaxAge(30*60);
					lastname.setMaxAge(30*60);
					
					resp.addCookie(firstname);
					resp.addCookie(lastname);
					resp.sendRedirect("Welcome.jsp");
				}
				else {
					resp.sendRedirect("Login.jsp");
				}
			}
			else {
			    resp.sendRedirect("Login.jsp");
			}
			} catch (SQLException se) {
				se.printStackTrace();
				resp.sendRedirect("Login.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("Login.jsp");
			}
	}

		private static final String query =
		"select * from user where email=?";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
