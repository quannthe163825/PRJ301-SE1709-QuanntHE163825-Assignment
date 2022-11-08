<%-- 
    Document   : list
    Created on : Sep 30, 2022, 3:23:59 PM
    Author     : Ngo Tung Son
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function removeStudent(id)
            {
                var result = confirm("are you fucking sure?");
                if(result)
                    window.location.href="delete?id="+id;
            }
        </script>
    </head>
    <body>
        <table>
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Gender</td>
                <td>Dob</td>
                <td>Created Time</td>
                <td></td>
                <td></td>
            </tr>
            <c:forEach items="${requestScope.students}" var="s">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.name}</td>
                    <td>${s.gender}</td>
                    <td>${s.dob}</td>
                    <td>${s.created_time}</td>
                    <td>
                        <a href="update?id=${s.id}">Edit</a>
                    </td>
                    <td>
                        <a href="#" onclick="removeStudent(${s.id})">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <a href="insert">Insert</a>
    </body>
</html>
