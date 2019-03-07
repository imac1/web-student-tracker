package jdbc2;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet({"/Ts2"})
public class Ts2 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = this.dataSource.getConnection();
            String sql = "select * from student";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);

            while(myRs.next()) {
                String email = myRs.getString("email");
                out.println(email);

            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }
        try {
            myConn.close();
            myStmt.close();
            myRs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
