package us.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;

@Entity
@Table(name = "`user`")
@JavaBean
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 128)
    @Comment("The email for this user.")
    private String email;

    @Column(name = "username", nullable = false, length = 32)
    @Comment("The username for this user.")
    private String username;

    @Column(name = "auth_string", nullable = false, length = 80)
    @Comment("The authentication string for this user.")
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

    public void setUsername(String name) {
        this.username = name;
    }

    public String getAuthString() {
        return authString;
    }

    public void setAuthString(String authString) {
        this.authString = authString;
    }
}
