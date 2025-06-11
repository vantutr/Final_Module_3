package com.example.library_management.controller;

import com.example.library_management.dao.BookDAO;
import com.example.library_management.dao.BorrowCardDAO;
import com.example.library_management.model.BorrowCard;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReturnServlet", urlPatterns = "/return")
public class ReturnServlet extends HttpServlet {
    private final BorrowCardDAO borrowCardDAO = new BorrowCardDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "confirm_return":
                returnBook(request, response);
                break;
            case "search":
                searchBorrowedBooks(request, response);
                break;
            default:
                showBorrowedList(request, response);
        }
    }

    private void showBorrowedList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BorrowCard> borrowedList = borrowCardDAO.selectAllBorrowedCards();
        request.setAttribute("borrowedList", borrowedList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/borrowedList.jsp");
        dispatcher.forward(request, response);
    }

    private void searchBorrowedBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookName = request.getParameter("bookNameSearch");
        String studentName = request.getParameter("studentNameSearch");
        List<BorrowCard> borrowedList = borrowCardDAO.search(bookName, studentName);
        request.setAttribute("borrowedList", borrowedList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/borrowedList.jsp");
        dispatcher.forward(request, response);
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String borrowId = request.getParameter("borrowId");
        String bookId = request.getParameter("bookId");

        borrowCardDAO.updateReturnStatus(borrowId);
        bookDAO.updateBookQuantity(bookId, 1);

        response.sendRedirect(request.getContextPath() + "/return");
    }
}