package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentTest {
    @Test
    void TestComment() {
        Comment mockComment = new Comment("", "");

        // Testing the comments
        assertEquals("", mockComment.getComment());
        mockComment.setComment("123");
        assertEquals("123", mockComment.getComment());

        // Testing the username
        assertEquals("", mockComment.getUserName());
        mockComment.setUserName("abc");
        assertEquals("abc", mockComment.getUserName());

    }

}
