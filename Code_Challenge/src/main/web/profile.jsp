<%--
  Created by IntelliJ IDEA.
  User: newmudhar
  Date: 3/16/2019
  Time: 12:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>Hello Mudhar</title>
</head>
<body>

    <form action="http://localhost:8080/Code_Challenge_Web_exploded/giphySearch" method="get">
        <input type="text" name="keyword"/><br/><br/>
        <input type="submit" value="Search"/>

    </form>

    <%
        ArrayList eList = (ArrayList)request.getSession().getAttribute("eList");
    %>

    <c:forEach items="${eList}" var="url">
        <tr>
            <tr>
                <td><img src="${url}"/> </td>
                <td><input type="text" name="category"/></td>
                <td><input type="submit" value="Save"/></td>
            </tr>

        </tr>
    </c:forEach>
</body>
</html>
