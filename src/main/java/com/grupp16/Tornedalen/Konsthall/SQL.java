package com.grupp16.Tornedalen.Konsthall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*; //NYTT
import java.util.*; //NYTT

import javax.sql.DataSource;

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

    //Hämta alla utställningar från databasen
    public List<Map<String, Object>> fetchAllExhibitions ()
    {
        String query = "SELECT * FROM exhibitions";

        //Skapar en tom lista som fylls med alla utställningar
        List<Map<String, Object>> exhibitions = new ArrayList<>();

        //Försöker ansluta till databasen och köra SQL-satsen
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            // Lopar igenom varje rad i resultatet (en rad lika med en utställning i databasen)
            while (rs.next())
            {
                // Skapar en karta där varje kollumnnamn är en nyckel och varje cell ett värde
                Map<String, Object> exhibition = new HashMap<>();

                //Lägger till alla kolumner vi vill hämta från DB
                exhibition.put("exhibitionID", rs.getInt("exhibitionID"));
                exhibition.put("name", rs.getString("name"));
                exhibition.put("artist", rs.getString("artist"));
                exhibition.put("startDate", rs.getDate("startDate"));
                exhibition.put("endDate", rs.getDate("endDate"));
                exhibition.put("description", rs.getString("description"));
                exhibition.put("active", rs.getBoolean("active"));
                exhibition.put("imageURL", rs.getString("imageURL"));

                //Lägger till de funna utställningarna i listan
                exhibitions.add(exhibition);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return exhibitions;

    }

}
