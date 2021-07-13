<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>PersonList</title>
</head>
<body>
<script type="text/javascript">
    <%@include file="/WEB-INF/table.js"%>
</script>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: tomato">
        <ul class="navbar-nav">
            <li><a id="linkList" onclick="createTable()"
                   class="nav-link">Persons</a></li>
        </ul>
    </nav>
</header>
<br>

<div class="row">
    <div class="container">
        <h3 class="text-center">Список людей</h3>
        <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/ServletJndi/new" class="btn btn-success">Добавить
                нового человека</a>
        </div>
        <br>
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col">id</th>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
                <th scope="col">Действие</th>
            </tr>
            </thead>
            <tbody id="tbody">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
