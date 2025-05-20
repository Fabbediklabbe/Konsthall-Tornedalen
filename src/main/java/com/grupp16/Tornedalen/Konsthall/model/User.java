package com.grupp16.Tornedalen.Konsthall.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userID;
    private String name;
    private String lastName;
    private String email;
    private String password;
 
 
    // Getters
    public int getUserID() {
        return userID;
    }
    public String getName () {
        return name;
    }
    public String getLastName()
    {
        return lastName;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPassword ()
    {
        return password;
    }
 
    // Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
