<%-- 
    Document   : insert
    Created on : Oct 3, 2022, 3:25:10 PM
    Author     : Ngo Tung Son
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="insert" method="POST">
            Name:<input type="text" name="name"/> <br/>
            Gender: <input type="radio" name="gender" value ="male" checked="checked"/> Male
            <input type="radio" name="gender" value="female"/> Female <br/>
            Dob: <input type="date" name="dob" /> <br/>
            
            <c:forEach items="${requestScope.skills}" var="s">
                <input type="checkbox" name="skid" value="${s.id}"/> ${s.name} <br/>
            </c:forEach>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
