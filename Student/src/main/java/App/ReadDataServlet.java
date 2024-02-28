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

@WebServlet("/read-data")
public class ReadDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/new";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "tiger";

    // Load the MySQL JDBC driver class
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver. Please check your classpath.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM registration WHERE Name=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                try (ResultSet rs = stmt.executeQuery()) {
                    PrintWriter out = response.getWriter();
                    if (rs != null && rs.next()) {
                    	out.println("<!DOCTYPE html>");
                    	out.println("<html lang=\"en\">");
                    	out.println("<head>");
                    	out.println("<meta charset=\"UTF-8\">");
                    	out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                    	out.println("<title>Student Data</title>");
                    	out.println("<style>");
                    	out.println("body {");
                    	out.println("    font-family: Arial, sans-serif;");
                    	out.println("    background-color: #f4f4f4;");
                    	out.println("    margin: 0;");
                    	out.println("    padding: 0;");
                    	out.println("}");
                    	out.println("container {");
                    	out.println("    width: 80%;");
                    	out.println("    margin: 50px auto;");
                    	out.println("    background-color: #fff;");
                    	out.println("    padding: 20px;");
                    	out.println("    border-radius: 5px;");
                    	out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
                    	out.println("}");
                    	out.println("h2 {");
                    	out.println("    text-align: center;");
                    	out.println("}");
                    	out.println("p {");
                    	out.println("    margin-bottom: 10px;");
                    	out.println("}");
                    	out.println("button {");
                    	out.println("    background-color: #4caf50;");
                    	out.println("    color: white;");
                    	out.println("    padding: 10px 20px;");
                    	out.println("    border: none;");
                    	out.println("    border-radius: 5px;");
                    	out.println("    cursor: pointer;");
                    	out.println("}");
                    	out.println("button:hover {");
                    	out.println("    background-color: #45a049;");
                    	out.println("}");
                    	out.println("</style>");
                    	out.println("</head>");
                    	out.println("<body>");
                    	out.println("<div class=\"container\">");
                    	out.println("<h2>Data Found:</h2>");
                    	out.println("<p><strong>ID:</strong> " + rs.getInt("ID") + "</p>");
                    	out.println("<p><strong>Name:</strong> " + rs.getString("Name") + "</p>");
                    	out.println("<p><strong>Email:</strong> " + rs.getString("Email") + "</p>");
                    	out.println("<p><strong>Date of Birth:</strong> " + rs.getDate("DateOfBirth") + "</p>");
                    	out.println("<button onclick=\"window.location.href='Index.html'\">Return</button>");
                    	out.println("</div>");
                    	out.println("</body>");
                    	out.println("</html>");

                    } else {
                        // No data found
                        out.println("<h2>No Data Found for Name: " + name + "</h2>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
