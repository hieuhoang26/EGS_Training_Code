package org.example.repository;


import org.example.config.DbConfig;
import org.example.dto.UserPost;
import org.example.model.Post;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    // Create User + Post
    public void createUserPosts(User user, List<Post> posts){
        String sql1 = "INSERT INTO users(username, email, pass) VALUES(?, ?, ?)";
        String sql2 = "INSERT INTO posts(user_id, title, content) VALUES(?, ?, ?)";

        Connection conn = null;
        try {
            conn = DbConfig.getConnection();
            conn.setAutoCommit(false); // start connection

            try(PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPass());
                int editedRow = ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)){
                for (Post p : posts) {
                    ps.setInt(1, user.getId());
                    ps.setString(2, p.getTitle());
                    ps.setString(3, p.getContent());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
        }catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            throw new RuntimeException("Transaction failed, rollback executed", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ignored) {}
            }
        }
    }
    // Insert posts (batch)
    public void saveBatch(List<Post> posts) {
        String sql = "INSERT INTO posts(user_id, title, content) VALUES(?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int batchSize = 0;

            for (Post p : posts) {
                ps.setInt(1, p.getUserId());
                ps.setString(2, p.getTitle());
                ps.setString(3, p.getContent());
                ps.addBatch();

                batchSize++;

                if (batchSize == 100) {
                    ps.executeBatch();
                    batchSize = 0;
                }
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Find posts by user Id
    public List<UserPost> findUserWithPostsJoin(int userId) {
        String sql = """
        SELECT u.id AS user_id, u.username, u.email,
               p.id AS post_id, p.title
        FROM users u
        JOIN posts p ON u.id = p.user_id
        WHERE u.id = ?
        ORDER BY p.created_at DESC
    """;

        List<UserPost> list = new ArrayList<>();

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserPost(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("post_id"),
                        rs.getString("title")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }



}
