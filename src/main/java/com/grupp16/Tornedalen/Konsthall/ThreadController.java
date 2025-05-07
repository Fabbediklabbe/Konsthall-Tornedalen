package com.grupp16.Tornedalen.Konsthall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class ThreadController {

    private static final Logger logger = LoggerFactory.getLogger(ThreadController.class);

    private final SQL sql;

    public ThreadController(SQL sql) {
        this.sql = sql;
    }

    // Hämta alla trådar
    @GetMapping
    public ResponseEntity<List<ThreadPost>> getAllThreads() {
        List<ThreadPost> threads = sql.getAllThreads();
        logger.info("Hämtade {} trådar", threads.size());
        return ResponseEntity.ok(threads);
    }

    // Skapa en ny tråd
    @PostMapping
    public ResponseEntity<String> createThread(@RequestBody ThreadPost thread) {
        try {
            thread.setCreatedAt(LocalDateTime.now());
            sql.createThread(thread);
            logger.info("Ny tråd skapad: {}", thread.getTitle());
            return ResponseEntity.ok("Tråden skapades!");
        } catch (Exception e) {
            logger.error("Fel vid skapande av tråd: {}", e.getMessage());
            return ResponseEntity.status(500).body("Kunde inte skapa tråd: " + e.getMessage());
        }
    }

    // Hanterar GET-förfrågningar till /api/threads
    // Returnerar en specifik tråd samt tillhörande utställning baserat på trådens ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getThreadWithExhibition(@PathVariable int id)
    {
        //Hämtar tråden från databasen via dess ID
        ThreadPost thread = sql.getThreadById(id);

        //Om tråden inte finns, returnera 404 Not Found
        if (thread == null) return ResponseEntity.notFound().build();

        // Hämta utställningen som tråden är kopplad till, baserat på trådens exhibitionName
        var exhibition = sql.fetchExhibitionsByName(thread.getExhibitionName());

        //Skapa ett svar som kombinerar trådinformationen och utställningsinformationen
        var response = new java.util.HashMap<String, Object>();

        //Lägg till tråden i svaret
        response.put("thread", thread);
        //Lägg till Utställningen i svaret
        response.put("exhibition", exhibition);

        //Skickar tillbaka svaret som JSON med HTTP status 200 OK
        return ResponseEntity.ok(response);
    }
} 
