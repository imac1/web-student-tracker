<%--
  Created by IntelliJ IDEA.
  User: Imac
  Date: 12/24/2018
  Time: 11:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add student form</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
    <link type="text/css" rel="stylesheet" href="css/add-student-style.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>university of kent</h2>
    </div>
</div>
<div id="container">
    <h3>add student</h3>
    <form action="StudentControllerServlet" method="GET">
        <input type="hidden" name="command" value="ADD">
        <table>
            <tbody>
            <tr>
                <td><label>First Name</label></td>
                <td><input type="text" name="firstName"></td>
            </tr>
            <tr>
                <td><label>Last Name</label></td>
                <td><input type="text" name="lastName"></td>
            </tr>
            <tr>
                <td><label>Email</label></td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td><label>submit</label></td>
                <td><input type="submit" value="save" class="save"></td>
            </tr>
            </tbody>
        </table>
    </form>
    <div style="clear: both;"></div>
    <p> <a href="StudentControllerServlet"> back to list</a></p>
</div>
</body>
</html>
