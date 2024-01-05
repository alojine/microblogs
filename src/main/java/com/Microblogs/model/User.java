package com.Microblogs.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "username", nullable = false)
    @Setter
    @Getter
    private String username;

    @Column(name = "email", nullable = false)
    @Setter
    @Getter
    private String email;

    @Column(name = "password", nullable = false)
    @Setter
    @Getter
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', username='%s', email='%s', created_at='%s', updated_at='%s']",
                id, username, email, createdAt, updatedAt);
    }
}
