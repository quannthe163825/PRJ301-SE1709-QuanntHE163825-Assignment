<%-- 
    Document   : update
    Created on : Oct 7, 2022, 2:57:55 PM
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
        <form action="update" method="POST">
            <input type="hidden" name="id" value="${requestScope.student.id}"/>
            Name:<input type="text" value="${requestScope.student.name}" name="name"/> <br/>
            Gender: 
            <input type="radio"
                   <c:if test="${requestScope.student.gender}">
                   checked="checked"
                   </c:if>
                   name="gender" value ="male"/> Male
            <input type="radio" 
                   <c:if test="${!requestScope.student.gender}">
                   checked="checked"
                   </c:if>
                   name="gender" value="female"/> Female <br/>
            Dob: <input type="date" value="${requestScope.student.dob}" name="dob" /> <br/>
            Department: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option 
                        <c:if test="${requestScope.student.dept.id eq d.id}">
                        selected="selected"
                        </c:if>
                        value="${d.id}">${d.name}</option>
                </c:forEach>
            </select> <br/>
            Skills: <br/>
            <c:forEach items="${requestScope.skills}" var="s">
                <input
                    <c:forEach items="${requestScope.student.skills}" var="sk">
                        <c:if test="${s.id eq sk.id}">
                            accept="" checked="checked"
                        </c:if>
                    </c:forEach>
                    type="checkbox" name="skid" value="${s.id}"/> ${s.name} <br/>
            </c:forEach>
            <input type="submit" value="Save"/>
        </form>
    </body>
</html>
