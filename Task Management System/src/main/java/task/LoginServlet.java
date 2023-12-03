package task;

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
			String username = req.getParameter("username");
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet result = ps.executeQuery();
			
			if(result.next()) {
				if (password.equals(result.getString("password"))) {	
					HttpSession session = req.getSession();
					session.setAttribute("id", true);
					
					session.setMaxInactiveInterval(30*60);
					Cookie username1 = new Cookie("id", result.getString("id"));
	
					username1.setMaxAge(30*60);
					
					
					resp.addCookie(username1);
					resp.sendRedirect("dashboard.jsp");
				}
				else {
					resp.sendRedirect("login.jsp");
				}
			}
			else {
			    resp.sendRedirect("login.jsp");
			}
			} catch (SQLException se) {
				se.printStackTrace();
				resp.sendRedirect("login.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("login.jsp");
			}
	}

		private static final String query =
		"select * from user where username=?";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
