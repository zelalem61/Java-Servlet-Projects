package task;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/updateTaskStatus")
public class UpdateTaskStatus extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 try {
             Connection conn = DatabaseConnection.getConnection();

             String taskId = request.getParameter("taskId");
             boolean isCompleted = Boolean.parseBoolean(request.getParameter("isCompleted"));
             int isCompletedUpdate = 0;
             if (isCompleted) isCompletedUpdate = 1;
             
             System.out.println("Task ID: " + taskId);
             System.out.println("Is Completed: " + isCompleted);

             // Update the database based on taskId and isCompleted
             String updateQuery = "UPDATE task SET completed=? WHERE id=?";
            
        	 PreparedStatement ps = conn.prepareStatement(updateQuery);
             ps.setInt(1, isCompletedUpdate);
             ps.setString(2, taskId);
             int count = ps.executeUpdate();

             // Send a JSON response indicating the success or failure
             String jsonResponse = "{\"success\":" + (count == 1) + "}";
             response.setContentType("application/json");
             PrintWriter out = response.getWriter();
             out.println(jsonResponse);
            
         } catch (SQLException se) {
             se.printStackTrace();
             response.sendRedirect("dashboard.jsp");
         } catch (Exception e) {
             e.printStackTrace();
             response.sendRedirect("dashboard.jsp");
         }
    }}

