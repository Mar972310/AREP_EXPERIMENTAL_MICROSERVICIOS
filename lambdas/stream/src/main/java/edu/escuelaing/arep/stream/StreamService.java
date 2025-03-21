package edu.escuelaing.arep.stream;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StreamService {
    
    private static final Gson gson = new Gson();

    public static Stream getStream() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT posts FROM stream LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        if (rs.next()) {
            List<Long> posts = parsePosts(rs.getString("posts"));
            return new Stream(posts);
        }
        return createStream();
    }
    
    public static Stream createStream() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO stream (posts) VALUES ('[]')"; // JSON vacío
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        return new Stream(new ArrayList<>());
    }
    
    public static Stream addPostToStream(Long postId) throws SQLException, ClassNotFoundException {
        Stream stream = getStream();
        if (stream == null) {
            stream = createStream();
        }
        stream.getPosts().add(postId);
        updateStream(stream);
        return getStream();
    }

    public static Stream removePostFromStream(Long postId) throws SQLException, ClassNotFoundException {
        Stream stream = getStream();
        if (stream != null) {
            stream.getPosts().remove(postId);
            updateStream(stream);
        }
        return getStream();
    }

    private static void updateStream(Stream stream) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "UPDATE stream SET posts = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, gson.toJson(stream.getPosts())); // Guardar como JSON
        stmt.executeUpdate();
    }

    private static List<Long> parsePosts(String postsJson) {
        if (postsJson == null || postsJson.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return gson.fromJson(postsJson, new TypeToken<List<Long>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
