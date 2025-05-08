package com.grupp16.Tornedalen.Konsthall.model;

import java.time.LocalDateTime;

public class Comment {
    private String userName;
    private String commentText;
    private LocalDateTime createdAt;

    public Comment(String userName, String comment, LocalDateTime createdAt) {
        this.userName = userName;
        this.commentText = comment;
        this.createdAt = createdAt;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return commentText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters (valfritt om du inte behöver dem)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setComment(String comment) {
        this.commentText = comment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
