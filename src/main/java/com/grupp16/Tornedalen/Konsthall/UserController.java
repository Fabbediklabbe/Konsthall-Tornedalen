package com.grupp16.Tornedalen.Konsthall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final SQL sql;

    public UserController(SQL sql) {
        this.sql = sql;
    }

    // Registrera ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        logger.info("Received registration for: {}", user.getEmail());
    try {
        sql.registerUser(user);
        logger.info("Registration made for: {}", user.getEmail());
        return ResponseEntity.ok("Registration succesful!");
    } catch (Exception e) {
        logger.error("Registration failed due to {}: {}", user.getEmail(), e.getMessage());
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}
//Metod för att hantera GET-förfrågningar till /api/users/info
@GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Authentication authentication)
{

        //Hämtar den inloggade användarens email från Spring Security-sessionen
        String email = authentication.getName();

        try
        {
            //Hämtar användare från databasen baserat på email
            User user = sql.getUserByEmail(email);
            if (user == null)
            {
                //Hittas ingen användare returnera 404 Not Found.
                return ResponseEntity.notFound().build();
            }

            //Skapar en ny HasMap som returenas som JSON
            //Endast säkra fält (inga lösenord)
            Map<String, String> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("lastName", user.getLastName());
            data.put("email", user.getEmail());

            // Returnerar användar data med HTTP-status 200 OK
            return ResponseEntity.ok(data);
        }
        catch (SQLException e)
        {
            // Om något går fel med databasen, returnera felmeddelande och 500
            return ResponseEntity.status(500).body("fel vid hämtning av användare");
        }

}

@GetMapping("/comments")
    public ResponseEntity<?> getUserComments(Authentication authentication) {
        String email = authentication.getName();

        try {
            User user = sql.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body("Användare ej hittad");
            }

            List<Map<String, Object>> comments = sql.getCommentsByUserId(user.getUserID());
            return ResponseEntity.ok(comments);

        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Fel vid hämtning av kommentarer");
        }
    }



}
