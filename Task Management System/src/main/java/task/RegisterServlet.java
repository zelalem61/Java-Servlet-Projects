package task;

import java.io.IOException;
import java.sql.Connection;
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
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		PreparedStatement ps = conn.prepareStatement(query);
		
		ps.setString(1, username);
		ps.setString(2, password);
		
		int count = ps.executeUpdate();
		
		if(count == 1) {
		  resp.sendRedirect("login.jsp");
		}
		else {
		    resp.sendRedirect("register.jsp");
		}
		} catch (SQLException se) {
			se.printStackTrace();
			resp.sendRedirect("register.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("register.jsp");
		}
}

	private static final String query =
	"insert into user(username, password) values(?, ?)";
	@Override
	
	protected void doPost(

	HttpServletRequest req,
	HttpServletResponse resp) throws IOException {
	
	doGet(req, resp);
}
}
