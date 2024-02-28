
package App;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-data")
public class UpdateDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/new";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "tiger";

    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Failed to load JDBC driver.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchName = request.getParameter("searchName");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            // Search for the student by name
            String searchSql = "SELECT * FROM registration WHERE Name=?";
            try (PreparedStatement searchStmt = conn.prepareStatement(searchSql)) {
                searchStmt.setString(1, searchName);
                try (ResultSet rs = searchStmt.executeQuery()) {
                    if (rs.next()) {
                        // Student found, proceed with updating the data
                        String updateSql = "UPDATE registration SET Name=?, Email=?, DateOfBirth=? WHERE Name=?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setString(1, name);
                            updateStmt.setString(2, email);
                            updateStmt.setString(3, dob);
                            updateStmt.setString(4, searchName);
                            int rowsUpdated = updateStmt.executeUpdate();
                            PrintWriter out = response.getWriter();
                            if (rowsUpdated > 0) {
                                out.println("<h2>Data Updated Successfully!</h2>");
                            } else {
                                out.println("<h2>Failed to Update Data</h2>");
                            }
                        }
                    } else {
                        // Student not found
                        PrintWriter out = response.getWriter();
                        out.println("<h2>No student found with the given name.</h2>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}


