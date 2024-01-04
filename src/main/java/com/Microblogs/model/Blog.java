package com.Microblogs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class Blog {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinTable(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Override
    public String toString() {
        return String.format(
                "Blog[id = '%s', user = '%s', body = '%s', created_at = '%s', updated_at = '%s']",
                id, user.toString(), body, createdAt, updatedAt
        );
    }
}
