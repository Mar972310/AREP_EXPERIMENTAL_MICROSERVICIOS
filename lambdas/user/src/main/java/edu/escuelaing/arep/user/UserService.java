package edu.escuelaing.arep.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    
    public static User createUser(User user) throws SQLException, ClassNotFoundException {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO users (username) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getUsername());
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        return user;
    }
    

    public static List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM users";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getLong("id"), rs.getString("username")));
        }
        return users;
    }

    public static User getUserById(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new User(rs.getLong("id"), rs.getString("username"));
        }
        return null;
    }
}
