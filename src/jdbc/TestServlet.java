/*

package jdbc;



import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



*/
/*

*
 * Servlet implementation class TestServlet

*//*




@WebServlet("/TestServlet")

public class TestServlet extends HttpServlet {


    // Define datasource/connection pool for Resource Injection
    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;
    PoolProperties p = new PoolProperties();






*/
/*
*
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
*//*




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();

        dataSource.setPoolProperties(p);
        p.setUrl("jdbc:mysql://localhost:3306/web_student_tracker");
        p.setDriverClassName("com.mysql.cj.jdbc.Driver");
        p.setUsername("webstudent");
        p.setPassword("webstudent");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");


        // Step 1:  Set up the printwriter
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        // Step 2:  Get a connection to the database
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();

            // Step 3:  Create a SQL statements
            String sql = "select * from student";
            myStmt = myConn.createStatement();

            // Step 4:  Execute SQL query
            myRs = myStmt.executeQuery(sql);

            // Step 5:  Process the result set
            while (myRs.next()) {
                String email = myRs.getString("email");
                out.println(email);
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}


*/
