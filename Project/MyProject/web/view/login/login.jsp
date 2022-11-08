<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : login
    Created on : 08-11-2022, 22:37:49
    Author     : fpt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <form action="loginServlet" method="POST">
            <table>
                <c:if test="${er}">
                    <tr>
                        <td>Nhap sai thong tin</td>
                        <td></td>
                    </tr>
                </c:if>

                <tr>
                    <td>User Name: </td>
                    <td><input type="text" value="${user}" required="" name="user"/></td>
                </tr>
                <tr>
                    <td>Pass: </td>
                    <td><input type="text" value="${pass}" required="" name="pass"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Login"/></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </body>
</html>