<!-- markCompleted.jsp -->
<%@ page language="java" import="java.sql.PreparedStatement,java.sql.Connection,java.sql.SQLException" import="task.DatabaseConnection" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="index.css" />
    <meta charset="UTF-8">
    <title>Mark Completed Tasks</title>
    <style>
        .completed {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
    <%
        if (session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
        }

        try {
            Connection con = DatabaseConnection.getConnection();
            String[] completedTasks = request.getParameterValues("completedTasks");

            if (completedTasks != null && completedTasks.length > 0) {
                String markCompletedQuery = "UPDATE task SET completed = true WHERE id = ?";
                PreparedStatement markCompletedStatement = con.prepareStatement(markCompletedQuery);

                for (String taskId : completedTasks) {
                    markCompletedStatement.setString(1, taskId);
                    markCompletedStatement.addBatch();
                }

                int[] updatedRows = markCompletedStatement.executeBatch();

                
                boolean tasksMarked = false;
                for (int updatedRow : updatedRows) {
                    if (updatedRow > 0) {
                        tasksMarked = true;
                        break;
                    }
                }

                if (tasksMarked) {
                    out.println("<h1>Tasks Marked as Completed Successfully!</h1>");
                } else {
                    out.println("<h1>No Tasks Marked as Completed</h1>");
                }
            } else {
                out.println("<h1>No Tasks Selected</h1>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            out.println("<h1>Error Marking Tasks as Completed</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error Marking Tasks as Completed</h1>");
        }
    %>
    <a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>
