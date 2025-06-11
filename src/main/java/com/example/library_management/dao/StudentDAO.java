package com.example.library_management.dao;

import com.example.library_management.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM student;";

    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getString("class_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}