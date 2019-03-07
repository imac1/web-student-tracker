<%--
  Created by IntelliJ IDEA.
  User: Imac
  Date: 12/23/2018
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Student Tracker App</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<%--<%
    // get the students from the request object (sent by servlet)
    List<Student> theStudents =
            (List<Student>) request.getAttribute("STUDENT_LIST");
%>--%>
<body>
<div id="wrapper">
<div id="header">
    <h2>University</h2>
</div>
</div>

<div id="container">
    <div id="content">

        <!-- put a new button: add student -->
        <input type="button" value="Add Student"
        onclick="window.location.href='add-student-form.jsp';
return false;" class="add-student-button"/>


        <table>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Action</th>
                <th>Action2</th>
            </tr>
<%--<% for (Student tempStudent: theStudents){
    %>
          <tr>
              <td><%= tempStudent.getFirstName() %></td>
              <td><%= tempStudent.getLastName() %></td>
              <td><%= tempStudent.getEmail() %></td>
          </tr>
<%} %>--%>
            <c:forEach var="tempStudent" items="${STUDENT_LIST}">
                <!-- set up a link for each student -->
                <c:url var="tempLink" value="/StudentControllerServlet">
                    <c:param name="command" value="LOAD" />
                    <c:param name="studentId" value="${tempStudent.id}" />
                </c:url>

                    <!-- set up a link to delete students -->
                <c:url var="deleteLink" value="/StudentControllerServlet">
                    <c:param name="command" value="DELETE" />
                    <c:param name="studentId" value="${tempStudent.id}" />

                </c:url>
                <tr>
                    <td>${tempStudent.firstName}</td>
                    <td>${tempStudent.lastName}</td>
                    <td>${tempStudent.email}</td>
                    <td><a href="${tempLink}"> Update</a> </td>
                    <td><a href="${deleteLink}" onclick="if(!(confirm('Are you sure' +
                     ' you want to delete this student?')))return false"> Delete</a> </td>
                </tr>

            </c:forEach>

        </table>
    </div>
</div>


</body>
</html>
