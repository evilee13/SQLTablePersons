<%@ page import="com.bft.com.Person" %><%--
  Created by IntelliJ IDEA.
  User: Swainy
  Date: 07.07.2021
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/person-list.jsp"
                   class="nav-link">Persons</a></li>
        </ul>
    </nav>
</header>
<br>
<%Person person = (Person)request.getAttribute("person"); %>
<form action="<%= person== null?"insert":"update" %>" method="POST">
    <div class="form-row align-items-center">
        <input type="hidden" name="id" value="<%= person != null? person.getId():""%>">
        <div class="col-sm-3 my-1">
            <label class="sr-only" for="inlineFormInputName">firstName</label>
            <input type="text" name="firstName" value="<%= person != null? person.getFirstName():""%>"
                   class="form-control" id="inlineFormInputName" placeholder="Имя">
        </div>
        <div class="col-sm-3 my-1">
            <label class="sr-only" for="inlineFormInputGroupUsername">lastName</label>
            <div class="input-group">
                <input type="text" name="lastName" value="<%= person != null? person.getLastName():""%>"
                       class="form-control" id="inlineFormInputGroupUsername" placeholder="Фамилия">
            </div>
        </div>
        <div class="col-auto my-1">
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </div>
    </div>
</form>
<script type="text/javascript">
    <%@include file="/WEB-INF/table.js"%>
</script>
</body>
</html>
