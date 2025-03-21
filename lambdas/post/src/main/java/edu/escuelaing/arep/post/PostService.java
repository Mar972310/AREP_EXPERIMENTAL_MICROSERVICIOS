/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.arep.post;

/**
 *
 * @author maritzamonsalvebautista
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {

    public static Post createPost(Post post) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO posts (message, likes, user_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, post.getMessage());
        stmt.setInt(2, 0);
        stmt.setLong(3, post.getUser());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating post failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating post failed, no ID obtained.");
            }
        }
        return post;
    }


    public static List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM posts";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Post(rs.getLong("id"), rs.getString("message"), rs.getInt("likes"),rs.getLong("user_id")));
        }
        return posts;
    }

    public static List<Post> getPostsByIds(List<Long> ids) throws SQLException, ClassNotFoundException {
        List<Post> posts = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return posts;
        }
        
        for (Long id : ids) {
            Post post = getPostById(id);
            if (post != null) {
                posts.add(post);
            }
        }
        
        return posts;
    }


    public static Post getPostById(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM posts WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Post(rs.getLong("id"), rs.getString("message"), rs.getInt("likes"),rs.getLong("user_id"));
        }
        return null;
    }

    public static Post updatePost(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "UPDATE posts SET likes = ? WHERE id = ?";
        Post post = getPostById(id);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, post.getLikes()+1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
        return getPostById(id);
        
    }

    public static boolean deletePost(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "DELETE FROM posts WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        if (getPostById(id) == null){
            return true;
        }
        return false;
    }
}
