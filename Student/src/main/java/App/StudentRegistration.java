package App;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/application")
public class StudentRegistration extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String dobStr = req.getParameter("dob");

        // Convert dob string to java.util.Date
        java.util.Date dob = null;
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.getWriter().write("Invalid date format");
            return;
        }

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/new?user=root&password=tiger");

            // Insert data into the 'students' table
            String insertQuery = "INSERT INTO registration (ID, Name, Email, DateOfBirth) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, email);
                ps.setDate(4, new java.sql.Date(dob.getTime())); // Convert java.util.Date to java.sql.Date

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    resp.getWriter().write("Registration successful");
                } else {
                    // Registration failed
                    resp.getWriter().write("Registration failed");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
