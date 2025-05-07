package com.grupp16.Tornedalen.Konsthall;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class SQL {

    private final JdbcTemplate jdbc;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_ID = "userID";
    private static final String EXHIBITION_ID = "exhibitionID";
    private static final String CREATED_AT = "createdAt";

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

    // Hämta användare genom e-post (för inloggning)
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT userID, email, password FROM users WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt(USER_ID));
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
        String query = """
        SELECT exhibitionID, name, artist, startDate, endDate, description, active, imageURL
        FROM exhibitions
        """;
        List<Map<String, Object>> exhibitions = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> exhibition = new HashMap<>();
                exhibition.put(EXHIBITION_ID, rs.getInt(EXHIBITION_ID));
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

    public List<ThreadPost> getAllThreads() {
        String query = """
            SELECT t.*, e.name AS exhibitionName
            FROM threads t
            JOIN exhibitions e ON t.exhibitionID = e.exhibitionID
            ORDER BY t.createdAt DESC
        """;
    
        List<ThreadPost> threads = new ArrayList<>();
    
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                ThreadPost thread = new ThreadPost();
                thread.setThreadID(rs.getInt("threadID"));
                thread.setUserID(rs.getInt(USER_ID));
                thread.setExhibitionID(rs.getInt(EXHIBITION_ID));
                thread.setTitle(rs.getString("title"));
                thread.setContent(rs.getString("content"));
                thread.setCreatedAt(rs.getTimestamp(CREATED_AT).toLocalDateTime());
                thread.setExhibitionName(rs.getString("exhibitionName"));
    
                threads.add(thread);
            }
    
        } catch (SQLException e) {
            e.printStackTrace(); // byt ut mot logger om du vill
        }
    
        return threads;
    }
    

    public void createThread(ThreadPost thread) {
        String sql = "INSERT INTO threads (userID, exhibitionID, title, content, createdAt) VALUES (?, ?, ?, ?, ?)";
    
        jdbc.update(sql,
            thread.getUserID(),
            thread.getExhibitionID(),
            thread.getTitle(),
            thread.getContent(),
            Timestamp.valueOf(thread.getCreatedAt())
        );
    }

    public ThreadPost getThreadById(int threadID) {
        String query = """
            SELECT t.*, e.name AS exhibitionName
            FROM threads t
            JOIN exhibitions e ON t.exhibitionID = e.exhibitionID
            WHERE t.threadID = ?
            """;
    
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, threadID);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                ThreadPost thread = new ThreadPost();
                thread.setThreadID(rs.getInt("threadID"));
                thread.setUserID(rs.getInt(USER_ID));
                thread.setExhibitionID(rs.getInt(EXHIBITION_ID));
                thread.setTitle(rs.getString("title"));
                thread.setContent(rs.getString("content"));
                thread.setCreatedAt(rs.getTimestamp(CREATED_AT).toLocalDateTime());
                thread.setExhibitionName(rs.getString("exhibitionName"));
    
                return thread;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hämta kommentarer för en tråd
    public List<Comment> getCommentsByThreadId(int threadID) {
        List<Comment> comments = new ArrayList<>();
        String query = """
            SELECT u.name AS userName, c.comment, c.createdAt 
            FROM comments c
            JOIN users u ON c.userID = u.userID
            WHERE c.threadID = ? 
            ORDER BY c.createdAt ASC
        """;
    
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, threadID);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                comments.add(new Comment(
                    rs.getString("userName"),
                    rs.getString("comment"),
                    rs.getTimestamp(CREATED_AT).toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return comments;
    }
    

    // Spara en ny kommentar till en tråd
    public void saveComment(int userID, int threadID, String comment) {
        String sql = "INSERT INTO comments (userID, threadID, comment, createdAt) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, userID, threadID, comment, Timestamp.valueOf(LocalDateTime.now()));
    }

}
