package com.grupp16.Tornedalen.Konsthall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private SQL sql; // Här injiceras vår SQL-klass automatiskt

    // Registrera ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            sql.registerUser(user);
            return ResponseEntity.ok("Registrering lyckades!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registrering misslyckades: " + e.getMessage());
        }
    }

    // Logga in användare
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            User foundUser = sql.getUserByEmail(user.getEmail());

            if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.ok("Inloggning lyckades!");
            } else {
                return ResponseEntity.status(401).body("Fel e-post eller lösenord.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ett fel uppstod vid inloggning: " + e.getMessage());
        }
    }
}
