<%--
  Created by IntelliJ IDEA.
  User: newmudhar
  Date: 3/15/2019
  Time: 10:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>
    <form action="http://localhost:8080/Code_Challenge_Web_exploded/v1/rest/user/signup" method="post">
      First Name:<input type="text" name="fname"/><br/><br/>
      Lst Name:<input type="text" name="lname"/><br/><br/>
      Email:<input type="text" name="email"/><br/><br/>
      Username:<input type="text" name="username"/><br/><br/>
      Password:<input type="password" name="password"/><br/><br/>
      <input type="submit" value="Submit"/>
    </form>
</body>
</html>
