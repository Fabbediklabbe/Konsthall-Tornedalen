package com.grupp16.Tornedalen.Konsthall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.grupp16.Tornedalen.Konsthall.model.ThreadPost;
import com.grupp16.Tornedalen.Konsthall.model.User;
import com.grupp16.Tornedalen.Konsthall.service.SQL;

import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;


@Controller
public class PageController {

    private final SQL sql;
    private static final String ATTR_THREAD = "thread";

    public PageController(SQL sql) {
        this.sql = sql;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/exhibitions")
    public String showExhibitions() {
        return "exhibitions";
    }

    @GetMapping("/forum")
    public String showForum(Model model) {
        List<ThreadPost> threads = sql.getAllThreads();
        model.addAttribute("threads", threads);
        return "forum";
    }

    @GetMapping("/forum/new")
    public String showNewThreadForm(Model model) {
        List<Map<String, Object>> exhibitions = sql.fetchAllExhibitions();
        model.addAttribute("exhibitions", exhibitions);
        model.addAttribute(ATTR_THREAD, new ThreadPost());
        return "new-thread";
    }

    @PostMapping("/forum/new")
    public String createNewThread(@ModelAttribute ThreadPost threadPost, Principal principal) {
        try {
            User user = sql.getUserByEmail(principal.getName());
            threadPost.setUserID(user.getUserID());
            threadPost.setCreatedAt(LocalDateTime.now());
            sql.createThread(threadPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/forum";
    }

    @GetMapping("/forum/{id}")
    public String showThread(@PathVariable("id") int threadID, Model model) {
        ThreadPost thread = sql.getThreadById(threadID);
        if (thread == null) return "redirect:/forum";

        model.addAttribute(ATTR_THREAD, thread);
        model.addAttribute("comments", sql.getCommentsByThreadId(threadID));
        return ATTR_THREAD;
    }

    @PostMapping("/forum/{id}/comment")
    public String postComment(@PathVariable("id") int threadID, @RequestParam String comment, Principal principal) {
        try {
            User user = sql.getUserByEmail(principal.getName());
            sql.saveComment(user.getUserID(), threadID, comment);
        } catch (Exception e) {
            e.printStackTrace(); // byt gärna ut mot logger
        }
        return "redirect:/forum/" + threadID;
    }

    @GetMapping("/mypages")
    public String showMyPages() {
        return "mypages";
    }
}
