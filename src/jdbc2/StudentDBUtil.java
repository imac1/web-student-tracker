package jdbc2;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDBUtil {

    private DataSource dataSource;

    // servlet creates and passes theDataSource into dataSource
    public StudentDBUtil(DataSource theDataSource) {
        dataSource = theDataSource;

    }

    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // get a connection
            myConn = dataSource.getConnection();

            // create a sql statemenet
            String sql = "select * from student order by last_name";
            myStmt = myConn.createStatement();
            // execute query
            myRs = myStmt.executeQuery(sql);
            // process the result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                // create new student obj

                Student tempStudent = new Student(id, firstName, lastName, email);

                // add it to the list of students
                students.add(tempStudent);
            }
            return students;

        } finally {
            // close the jdbc objects
            close(myConn, myStmt, myRs);

        }
    }

    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {

            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
                // doesn't really close the connection, it makes it available
                // for smone else to use
                // puts it back in the connection pool
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student theStudent) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {
            // get db connection
            myConn = dataSource.getConnection();

            // create sql for insert
            String sql = "insert into student"
                    + "(first_name, last_name, email)"
                    + "values (?, ?, ?)";
            // ??? are placeholders for values in the table,
            // used in prepared statements
            myStmt = myConn.prepareStatement(sql);

            // set the param values for the student
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());

            // execute the sql insert
            myStmt.execute();

        } finally {
            // clean up the jdbc objects
            close(myConn, myStmt, null);

        }
    }

    public Student getStudent(String theStudentId) throws Exception {
        Student theStudent = null;

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int studentId;

        try {
            // convert student id to an int
            studentId = Integer.parseInt(theStudentId);

            // get connection to database
            myConn = dataSource.getConnection();

            // create sql to get selected student
            String sql = "select * from student where id=?";

            // create prepared statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, studentId);

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from the resultset row

            if (myRs.next()) {
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                // use the studentId during construction
                theStudent = new Student(studentId, firstName, lastName, email);
            } else {
                throw new Exception("could not find the student id: " + studentId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // clean the jdbc objects
            close(myConn, myStmt, myRs);

        }
        return theStudent;
    }

    public void updateStudent(Student theStudent) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // get db connection
            myConn = dataSource.getConnection();

            // create sql update statement
            String sql =  "update student "
                    + "set first_name=?, last_name=?, email=? "
                    + "where id=?";
            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());
            myStmt.setInt(4, theStudent.getId());

            // execute SQL statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC objects

            close(myConn, myStmt, null);

        }

    }

    public void deleteStudent(String theStudentId) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        try {

            // convert student id to an integer
            int studentId = Integer.parseInt(theStudentId);

            // get connection to the database
            myConn = dataSource.getConnection();

            // create sql to delete student
            String sql = "delete from student where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, studentId);

            // execute sql statement
            myStmt.execute();

        }
        finally {

            // clean up JDBC code
            close(myConn, myStmt, null);

        }
    }
}
