package com.example.library_management.dao;

import com.example.library_management.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM book;";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM book WHERE book_id = ?;";
    private static final String UPDATE_BOOK_QUANTITY = "UPDATE book SET quantity = quantity + ? WHERE book_id = ?;";

    public List<Book> selectAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_BOOKS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book selectBook(String bookId) {
        Book book = null;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_BOOK_BY_ID)) {
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new Book(
                        rs.getString("book_id"),
                        rs.getString("book_name"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public boolean updateBookQuantity(String bookId, int amountChange) {
        boolean rowUpdated = false;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_BOOK_QUANTITY)) {
            ps.setInt(1, amountChange);
            ps.setString(2, bookId);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
}