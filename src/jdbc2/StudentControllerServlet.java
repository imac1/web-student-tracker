package jdbc2;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.List;

@WebServlet(name = "StudentControllerServlet", urlPatterns = "/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {

    private StudentDBUtil studentDBUtil;
    @Resource(name = "jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        // create our student db util and pass in the conn pool / datasource

        try{
            studentDBUtil = new StudentDBUtil(dataSource);
        } catch (Exception exc){
            throw new ServletException(exc);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // list the students in MVC fashion


        try {
            // read the "command" parameter
            String theCommand = request.getParameter("command");

            // if the command is missing, then default to listing students
            if (theCommand == null){
                theCommand = "LIST";
            }

            // route to the appropriate method
            switch (theCommand){

                case "LIST":
                    listStudents(request, response);
                    break;

                case "ADD":
                    addStudent(request, response);
                    break;
                case "LOAD":
                    loadStudent(request, response);
                    break;
                case "UPDATE":
                    updateStudent(request, response);
                    break;
                case "DELETE" :
                    deleteStudent(request, response);
                    break;

                    default:
                        listStudents(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //read student id from the form data
        String theStudentId = request.getParameter("studentId");

        // delete student from database
        studentDBUtil.deleteStudent(theStudentId);

        // send the user back to the "list students" page
        listStudents(request, response);

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // read student info from form data
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");


        // create a new student object
        Student theStudent = new Student(id, firstName, lastName, email);

        // perform update on database
        studentDBUtil.updateStudent(theStudent);

        // send them back oto the list students page
        listStudents(request, response);


    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // read student id form data
        String theStudentId = request.getParameter("studentId");

        // get student from database (db util)
        Student theStudent = studentDBUtil.getStudent(theStudentId);

        // place student in the request attribute
        request.setAttribute("THE_STUDENT", theStudent);

        //send to jsp page: update-student-form.jsp
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);

    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // read student info from the form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // create a new student obj
        Student theStudent = new Student(firstName, lastName, email);

        // add the ss ot the database
        studentDBUtil.addStudent(theStudent);

        // send back to main page (the ss list)

        listStudents(request, response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // get students from db util
        List<Student> students = studentDBUtil.getStudents();
        // add ss to the request
        request.setAttribute("STUDENT_LIST", students);
        // send to the jsp page(view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }
}
