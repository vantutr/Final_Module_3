package com.example.library_management.model;

import java.util.Date;

public class BorrowCard {
    private String borrowId;
    private boolean isReturned;
    private Date borrowDate;
    private Date returnDate;

    private Book book;
    private Student student;

    public BorrowCard() {
    }

    public BorrowCard(String borrowId, boolean isReturned, Date borrowDate, Date returnDate, Book book, Student student) {
        this.borrowId = borrowId;
        this.isReturned = isReturned;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
        this.student = student;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "BorrowCard{" +
                "borrowId='" + borrowId + '\'' +
                ", isReturned=" + isReturned +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", book=" + book +
                ", student=" + student +
                '}';
    }
}