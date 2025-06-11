<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Mượn Sách</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container col-md-6 mt-4">
    <div class="card">
        <div class="card-header">
            <h3 class="text-center">Form Mượn Sách</h3>
        </div>
        <div class="card-body">
            <form action="<c:url value='/borrow?action=confirm_borrow'/>" method="post">
                <div class="form-group">
                    <label>Mã mượn sách</label>
                    <input type="text" name="borrowId" class="form-control" placeholder="Ví dụ: MS-0001" required>
                </div>
                <div class="form-group">
                    <label>Tên sách</label>
                    <input type="text" class="form-control" value="<c:out value='${book.bookName}'/>" readonly>
                    <input type="hidden" name="bookId" value="<c:out value='${book.bookId}'/>">
                </div>
                <div class="form-group">
                    <label>Tên học sinh</label>
                    <select name="studentId" class="form-control">
                        <c:forEach var="student" items="${studentList}">
                            <option value="${student.studentId}"><c:out value="${student.studentName}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Ngày mượn</label>
                    <input type="text" class="form-control" value="<%= DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()) %>" readonly>
                </div>
                <div class="form-group">
                    <label>Ngày trả</label>
                    <input type="date" name="returnDate" class="form-control" required min="<%= LocalDate.now().plusDays(1) %>">
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-success">Xác nhận mượn</button>
                    <a href="<c:url value='/books'/>" class="btn btn-secondary">Trở về danh sách</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>