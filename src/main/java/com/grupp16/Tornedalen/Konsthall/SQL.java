package com.grupp16.Tornedalen.Konsthall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * This class runs SQL-Queries
 * It is a @Component so that Spring Boot can inject where it has to
 */

@Component
public class SQL {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private DataSource dataSource;

    // Registrera ny användare i databasen
    public void registerUser(User user) {
        String sql = "INSERT INTO users (name, lastName, email, password) VALUES (?,?,?,?)";
        jdbc.update(sql, user.getName(), user.getLastName(), user.getEmail(), user.getPassword());
    }

    // Hämta användare genom e-post (för inloggning)
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT email, password FROM users WHERE email = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null;
            }
        }
    }
}
