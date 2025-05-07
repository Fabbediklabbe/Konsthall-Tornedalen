package com.grupp16.Tornedalen.Konsthall;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class SQL {

    private final JdbcTemplate jdbc;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public SQL(JdbcTemplate jdbc, DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbc = jdbc;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrera ny användare i databasen (med hashat lösenord)
    public void registerUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        String sql = "INSERT INTO users (name, lastName, email, password) VALUES (?,?,?,?)";
        jdbc.update(sql, user.getName(), user.getLastName(), user.getEmail(), hashedPassword);
    }

    // Hämta användare genom e-post (FÖR INLOGGNING)
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT userID, name, lastName, email, password FROM users WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID")); //NYTT
                user.setName(rs.getString("name")); //NYTT
                user.setLastName(rs.getString("lastName")); //NYTT
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null;
            }
        }
    }

    // Hämta alla utställningar från databasen
    public List<Map<String, Object>> fetchAllExhibitions() {
        String query = "SELECT * FROM exhibitions";
        List<Map<String, Object>> exhibitions = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> exhibition = new HashMap<>();
                exhibition.put("exhibitionID", rs.getInt("exhibitionID"));
                exhibition.put("name", rs.getString("name"));
                exhibition.put("artist", rs.getString("artist"));
                exhibition.put("startDate", rs.getDate("startDate"));
                exhibition.put("endDate", rs.getDate("endDate"));
                exhibition.put("description", rs.getString("description"));
                exhibition.put("active", rs.getBoolean("active"));
                exhibition.put("imageURL", rs.getString("imageURL"));
                exhibitions.add(exhibition);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // eller logga snyggare
        }

        return exhibitions;
    }

    public List<Map<String, Object>> getCommentsByUserId (int userID)
    {
        List<Map<String, Object>> comments = new ArrayList<>();
        String query ="SELECT comment, createdAt FROM comments WHERE userID = ? ORDER BY createdAt DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Map<String, Object> comment = new HashMap<>();
                comment.put("comment", rs.getString("comment"));
                comment.put("createdAt", rs.getTimestamp("createdAt"));
                comments.add(comment);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return comments;

    }

}
