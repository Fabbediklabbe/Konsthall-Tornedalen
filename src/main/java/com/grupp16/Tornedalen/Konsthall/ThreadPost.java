package com.grupp16.Tornedalen.Konsthall;

import java.time.LocalDateTime;

public class ThreadPost {
    private int threadID;
    private int userID;
    private int exhibitionID;
    private String title;
    private String content;
    private String exhibitionName;
    private String userFirstName;
    private String userLastName;
    private LocalDateTime createdAt;

    // Getters
    public int getThreadID() { return threadID; }
    public int getUserID() { return userID; }
    public int getExhibitionID() { return exhibitionID; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getExhibitionName() { return exhibitionName; }
    public String getUserFirstName() { return userFirstName; }
    public String getUserLastName() { return userLastName; }

    // Setters
    public void setThreadID(int threadID) { this.threadID = threadID; }
    public void setUserID(int userID) { this.userID = userID; }
    public void setExhibitionID(int exhibitionID) { this.exhibitionID = exhibitionID; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setExhibitionName(String exhibitionName) { this.exhibitionName = exhibitionName; }
    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }
    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }
}
