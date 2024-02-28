package App;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/delete")
public class DeleteDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/new";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "tiger";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver. Please check your classpath.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String sql = "DELETE FROM registration WHERE Name=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                int rowsAffected = stmt.executeUpdate();

                PrintWriter out = response.getWriter();
                if (rowsAffected > 0) {
                	out.println("<!DOCTYPE html>");
                	out.println("<html lang=\"en\">");
                	out.println("<head>");
                	out.println("<meta charset=\"UTF-8\">");
                	out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                	out.println("<title>Data Deletion Success</title>");
                	out.println("<style>");
                	out.println("body {");
                	out.println("    font-family: Arial, sans-serif;");
                	out.println("    background-color: #f4f4f4;");
                	out.println("    margin: 0;");
                	out.println("    padding: 0;");
                	out.println("}");
                	out.println(".container {");
                	out.println("    width: 50%;");
                	out.println("    margin: 50px auto;");
                	out.println("    background-color: #fff;");
                	out.println("    padding: 20px;");
                	out.println("    border-radius: 5px;");
                	out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
                	out.println("    text-align: center;");
                	out.println("}");
                	out.println("h2 {");
                	out.println("    color: #4caf50;");
                	out.println("}");
                	out.println("</style>");
                	out.println("</head>");
                	out.println("<body>");
                	out.println("<div class=\"container\">");
                	out.println("<h2>Data with Name " + name + " deleted successfully</h2>");
                	out.println("</div>");
                	out.println("</body>");
                	out.println("</html>");

                } else {
                    out.println("<h2>No data found with Name " + name + "</h2>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
