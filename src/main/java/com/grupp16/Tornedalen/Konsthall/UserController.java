package com.grupp16.Tornedalen.Konsthall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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

}
