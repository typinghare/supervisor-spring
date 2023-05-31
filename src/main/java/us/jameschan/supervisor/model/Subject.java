package us.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;

@Entity
@Table(name = "subject")
@JavaBean
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    @Comment("The id of the user to which this subject belong.")
    private Long userId;

    @Column(name = "name", nullable = false, length = 32)
    @Comment("The name of this subject.")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
