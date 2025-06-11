package com.example.library_management.dao;

import com.example.library_management.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowCardDAO {

    public void insertBorrowCard(String borrowId, String bookId, String studentId, java.util.Date borrowDate, java.util.Date returnDate) {
        String query = "INSERT INTO borrow_card (borrow_id, book_id, student_id, is_returned, borrow_date, return_date) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, borrowId);
            ps.setString(2, bookId);
            ps.setString(3, studentId);
            ps.setBoolean(4, false);
            ps.setDate(5, new java.sql.Date(borrowDate.getTime()));
            ps.setDate(6, new java.sql.Date(returnDate.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BorrowCard> selectAllBorrowedCards() {
        String query = "SELECT bc.borrow_id, bc.is_returned, bc.borrow_date, bc.return_date, " +
                "b.book_id, b.book_name, s.student_id, s.student_name " +
                "FROM borrow_card bc " +
                "JOIN book b ON bc.book_id = b.book_id " +
                "JOIN student s ON bc.student_id = s.student_id " +
                "WHERE bc.is_returned = false;";
        return executeQueryForCards(query);
    }

    public List<BorrowCard> search(String bookName, String studentName) {
        String query = "SELECT bc.borrow_id, bc.is_returned, bc.borrow_date, bc.return_date, " +
                "b.book_id, b.book_name, s.student_id, s.student_name " +
                "FROM borrow_card bc " +
                "JOIN book b ON bc.book_id = b.book_id " +
                "JOIN student s ON bc.student_id = s.student_id " +
                "WHERE bc.is_returned = false AND b.book_name LIKE ? AND s.student_name LIKE ?;";
        List<BorrowCard> cards = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, "%" + bookName + "%");
            ps.setString(2, "%" + studentName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapResultSetToBorrowCard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public boolean updateReturnStatus(String borrowId) {
        String query = "UPDATE borrow_card SET is_returned = true WHERE borrow_id = ?;";
        boolean rowUpdated = false;
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, borrowId);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    private List<BorrowCard> executeQueryForCards(String query) {
        List<BorrowCard> cards = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cards.add(mapResultSetToBorrowCard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private BorrowCard mapResultSetToBorrowCard(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getString("book_id"));
        book.setBookName(rs.getString("book_name"));

        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setStudentName(rs.getString("student_name"));

        return new BorrowCard(
                rs.getString("borrow_id"),
                rs.getBoolean("is_returned"),
                rs.getDate("borrow_date"),
                rs.getDate("return_date"),
                book,
                student
        );
    }
}