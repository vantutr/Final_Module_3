<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Sách Đang Mượn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        .close-button {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close-button:hover,
        .close-button:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .modal-footer button {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container mt-4">
    <h2 class="text-center">Thông tin sách đang cho mượn</h2>
    <div class="row mb-3">
        <div class="col-md-6">
            <a href="<c:url value='/books'/>" class="btn btn-info">Quay lại trang chủ</a>
        </div>
        <div class="col-md-6">
            <form class="form-inline float-right" action="<c:url value='/return'/>" method="get">
                <input type="hidden" name="action" value="search">
                <input class="form-control mr-sm-2" type="search" placeholder="Tên sách" name="bookNameSearch" value="<c:out value='${param.bookNameSearch}'/>">
                <input class="form-control mr-sm-2" type="search" placeholder="Tên học sinh" name="studentNameSearch" value="<c:out value='${param.studentNameSearch}'/>">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Tìm kiếm</button>
            </form>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Mã mượn sách</th>
            <th>Tên sách</th>
            <th>Tên học sinh</th>
            <th>Ngày mượn</th>
            <th>Ngày trả</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="card" items="${borrowedList}">
            <tr>
                <td><c:out value="${card.borrowId}"/></td>
                <td><c:out value="${card.book.bookName}"/></td>
                <td><c:out value="${card.student.studentName}"/></td>
                <td><fmt:formatDate value="${card.borrowDate}" pattern="dd/MM/yyyy" type="date"/></td>
                <td><fmt:formatDate value="${card.returnDate}" pattern="dd/MM/yyyy" type="date"/></td>
                <td>
                    <button type="button" class="btn btn-success" onclick="openModal('returnConfirmModal${card.borrowId}')">
                        Trả sách
                    </button>

                    <div id="returnConfirmModal${card.borrowId}" class="modal">
                        <div class="modal-content">
                            <span class="close-button" onclick="closeModal('returnConfirmModal${card.borrowId}')">&times;</span>
                            <div class="modal-header">
                                <h5>Xác nhận trả sách</h5>
                            </div>
                            <div class="modal-body">
                                Học sinh <b><c:out value="${card.student.studentName}"/></b> thực hiện trả sách <b><c:out value="${card.book.bookName}"/></b>?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" onclick="closeModal('returnConfirmModal${card.borrowId}')">Không</button>
                                <a href="<c:url value='/return?action=confirm_return&borrowId=${card.borrowId}&bookId=${card.book.bookId}'/>" class="btn btn-primary">Trả Sách</a>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function openModal(modalId) {
        document.getElementById(modalId).style.display = "block";
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = "none";
        }
    }
</script>
</body>
</html>