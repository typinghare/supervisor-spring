package me.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;

@Entity
@Table(name = "user")
@JavaBean
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 128)
    @Comment("The email address.")
    private String email;

    @Column(name = "username", nullable = false, length = 32)
    @Comment("The username.")
    private String username;

    @Column(name = "auth_string", nullable = false, length = 128)
    @Comment("The authentication string.")
    private String authString;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthString() {
        return authString;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }
}
