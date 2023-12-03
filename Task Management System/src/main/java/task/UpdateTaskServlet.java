package task;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

@WebServlet("/update")
public class UpdateTaskServlet extends HttpServlet {
    public UpdateTaskServlet() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Connection conn = DatabaseConnection.getConnection();
			String query = "update task set title=?, description=?, due_date=?, priority_level=? where id=?";
			
			String title = req.getParameter("title");
			String description = req.getParameter("description");
			Date date = Date.valueOf(req.getParameter("due_date"));
			String priorityLevel = req.getParameter("priority_level");
			String id = req.getParameter("id");
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, title);
			ps.setString(2, description);
			ps.setDate(3, date);
			ps.setString(4, priorityLevel);
			ps.setString(5, id);
			
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
				
			  resp.sendRedirect("dashboard.jsp");
			}
			else {
			    resp.sendRedirect("update.jsp?id="+req.getParameter("id"));
			}
			} catch (SQLException se) {
				se.printStackTrace();
				resp.sendRedirect("update.jsp?id="+req.getParameter("id"));
				
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("update.jsp?id="+req.getParameter("id"));
			}
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
