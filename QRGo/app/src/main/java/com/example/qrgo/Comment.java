package com.example.qrgo;

import java.io.Serializable;

/**
 * This class allows the user to comment on user profiles
 */
public class Comment implements Serializable {
    private String userName;
    private String comment;

    /**
     * Takes in a parameter 'userName' and 'comment' and saves it to Comments attributes
     * @param userName
     * @param comment
     */
    public Comment(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }

    /**
     * This method gets the Comment's userName
     * @return
     * Returns the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method sets the Comment's userName
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method gets the comment attribute
     * @return
     * Returns the saved comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method sets the comment attribute
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
