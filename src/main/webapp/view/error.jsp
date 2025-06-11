<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lỗi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-danger text-center" role="alert">
        <h4 class="alert-heading">Lỗi!!!!</h4>
        <p><c:out value="${errorMessage}"/></p>
        <hr>
        <a href="<c:url value='/books'/>" class="btn btn-primary">OK</a>
    </div>
</div>
</body>
</html>