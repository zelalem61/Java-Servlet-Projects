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

@WebServlet("/delete")
public class DeleteTaskServlet extends HttpServlet {

    public DeleteTaskServlet() {
        super();
     }


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Connection conn = DatabaseConnection.getConnection();
			String query = "delete from task where id=?";
			
			String id = req.getParameter("id");
			if (id == null) resp.sendRedirect("login.jsp");
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, id);
			
			int count = ps.executeUpdate();
			
			if(count == 1) {
			  resp.sendRedirect("dashboard.jsp");
			}
			else {
			    resp.sendRedirect("dashboard.jsp");
			}
			} catch (SQLException se) {
				se.printStackTrace();
				resp.sendRedirect("dashboard.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				resp.sendRedirect("dashboard.jsp");
			}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
