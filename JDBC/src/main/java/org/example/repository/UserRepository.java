package org.example.repository;

import org.example.config.DbConfig;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    // CREATE
    public void save(User user) {
        String sql = "INSERT INTO users(username, email, pass) VALUES(?, ?, ?)";
        try (
                Connection conn = DbConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());

            int editedRow = ps.executeUpdate();

            if (editedRow == 0) {
                throw new SQLException("Creating user failed");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    // GET ALL
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (
                Connection conn = DbConfig.getConnection();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql);
        ) {
//            Thread.sleep(4000);
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            return null;
        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return users;
    }

    // GET BY ID
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (
                Connection conn = DbConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, id);  // cursor to first line
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            return null;
        }
        return Optional.empty();
    }
    // UPDATE
    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, pass = ? WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPass());
            ps.setInt(4, user.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }
    // DELETE -  ID
    public boolean deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        int id = rs.getInt("id");
        user.setId(rs.wasNull() ? 0 : id);
        user.setUsername(getNullableString(rs, "username"));
        user.setEmail(getNullableString(rs, "email"));
        user.setPass(getNullableString(rs, "pass"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        } else {
            user.setCreatedAt(null);
        }
        return user;
    }

    private String getNullableString(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return rs.wasNull() ? null : value;
    }


}
