package task;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateprofile")
public class UpdateProfileServlet extends HttpServlet {
    private static final String UPDATE_BOTH_QUERY = "UPDATE user SET username=?, password=? WHERE id=?";
    private static final String UPDATE_USERNAME_QUERY = "UPDATE user SET username=? WHERE id=?";
    private static final String UPDATE_PASSWORD_QUERY = "UPDATE user SET password=? WHERE id=?";
    private static final String SELECT_USER_ID_QUERY = "SELECT id FROM user WHERE username=? AND password=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	if(req.getSession().getAttribute("id") == null){
			resp.sendRedirect("login.jsp");
		}
        try {
            Connection conn = DatabaseConnection.getConnection();

            String username = req.getParameter("username");
            String password = req.getParameter("password");

            String id = null;
			Cookie[] cookies = req.getCookies();
			if(cookies != null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("id")) id = cookie.getValue();
				}
			}
			
			if (id == null) resp.sendRedirect("login.jsp");
           

            int count = 0;

            if ((username != null && !username.equals("")) &&( password != null && !password.equals("") )) {
                PreparedStatement selectIdStatement = conn.prepareStatement(SELECT_USER_ID_QUERY);
                selectIdStatement.setString(1, username);
                selectIdStatement.setString(2, password);
                ResultSet resultSet = selectIdStatement.executeQuery();

                if (resultSet.next()) {
                    id = resultSet.getString("id");
                }

                PreparedStatement updateBothStatement = conn.prepareStatement(UPDATE_BOTH_QUERY);
                updateBothStatement.setString(1, username);
                updateBothStatement.setString(2, password);
                updateBothStatement.setString(3, id);
                count = updateBothStatement.executeUpdate();
            } else if (username != null && !username.equals("")) {
            	
                PreparedStatement updateUsernameStatement = conn.prepareStatement(UPDATE_USERNAME_QUERY);
                updateUsernameStatement.setString(1, username);
                updateUsernameStatement.setString(2, id);
                count = updateUsernameStatement.executeUpdate();
            } else if (password != null && !password.equals("")) {
                PreparedStatement updatePasswordStatement = conn.prepareStatement(UPDATE_PASSWORD_QUERY);
                updatePasswordStatement.setString(1, password);
                updatePasswordStatement.setString(2, id);
                count = updatePasswordStatement.executeUpdate();
            } else {
                resp.sendRedirect("updateprofile.jsp");
            }

            if (count == 1) {
                resp.sendRedirect("dashboard.jsp");
            } else {
                resp.sendRedirect("updateprofile.jsp");
            }

        } catch (SQLException se) {
            se.printStackTrace();
            resp.sendRedirect("updateprofile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("updateprofile.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
