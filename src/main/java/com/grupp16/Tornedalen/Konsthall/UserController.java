package com.grupp16.Tornedalen.Konsthall;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SQL sql;

    public UserController(SQL sql) {
        this.sql = sql;
    }

    // Registrera ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
    System.out.println("📥 Mottog registreringsförsök för: " + user.getEmail());
    try {
        sql.registerUser(user);
        System.out.println("✅ Registrerad i databasen");
        return ResponseEntity.ok("Registrering lyckades!");
    } catch (Exception e) {
        System.out.println("❌ Registrering misslyckades: " + e.getMessage());
        return ResponseEntity.badRequest().body("Fel: " + e.getMessage());
    }
}

}
