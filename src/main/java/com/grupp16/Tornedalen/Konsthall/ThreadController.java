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
} 
