<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách Sách</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h2 class="text-center">Danh sách Sách trong thư viện</h2>
    <div class="mb-3">
        <a href="<c:url value='/return'/>" class="btn btn-info">Xem danh sách sách đang mượn</a>
    </div>
    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Mã sách</th>
            <th>Tên sách</th>
            <th>Tác giả</th>
            <th>Mô tả</th>
            <th>Số lượng</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="book" items="${bookList}">
            <tr>
                <td><c:out value="${book.bookId}"/></td>
                <td><c:out value="${book.bookName}"/></td>
                <td><c:out value="${book.author}"/></td>
                <td><c:out value="${book.description}"/></td>
                <td><c:out value="${book.quantity}"/></td>
                <td>
                    <a href="<c:url value='/borrow?action=borrow&bookId=${book.bookId}'/>" class="btn btn-primary" <c:if test="${book.quantity == 0}">disabled</c:if>>
                        Mượn sách
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>