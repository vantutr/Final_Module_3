package com.example.library_management.controller;

import com.example.library_management.dao.*;
import com.example.library_management.model.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BorrowServlet", urlPatterns = "/borrow")
public class BorrowServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final BorrowCardDAO borrowCardDAO = new BorrowCardDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && "borrow".equals(action)) {
            showBorrowForm(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/books");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("confirm_borrow".equals(action)) {
            confirmBorrow(request, response);
        }
    }

    private void showBorrowForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookId = request.getParameter("bookId");
        Book book = bookDAO.selectBook(bookId);

        if (book.getQuantity() == 0) {
            request.setAttribute("errorMessage", "Cuốn sách này đã hết, vui lòng chọn sách khác.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/error.jsp");
            dispatcher.forward(request, response);
        } else {
            List<Student> studentList = studentDAO.selectAllStudents();
            request.setAttribute("book", book);
            request.setAttribute("studentList", studentList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/borrowForm.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void confirmBorrow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String borrowId = request.getParameter("borrowId");
        String bookId = request.getParameter("bookId");
        String studentId = request.getParameter("studentId");

        Date borrowDate = new Date();

        Date returnDate = null;
        try {
            returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("returnDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        borrowCardDAO.insertBorrowCard(borrowId, bookId, studentId, borrowDate, returnDate);
        bookDAO.updateBookQuantity(bookId, -1);

        response.sendRedirect(request.getContextPath() + "/books");
    }
}