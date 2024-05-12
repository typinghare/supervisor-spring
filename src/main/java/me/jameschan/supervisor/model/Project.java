package me.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;
import java.sql.Timestamp;

@Entity
@Table(name = "project")
@JavaBean
public class Project {
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

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "started_at")
    private Timestamp started_at;

    @Column(name = "ended_at")
    private Timestamp ended_at;

    @Column(name = "archived_at")
    private Timestamp archived_at;

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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getStarted_at() {
        return started_at;
    }

    public void setStarted_at(Timestamp started_at) {
        this.started_at = started_at;
    }

    public Timestamp getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(Timestamp ended_at) {
        this.ended_at = ended_at;
    }

    public Timestamp getArchived_at() {
        return archived_at;
    }

    public void setArchived_at(Timestamp archived_at) {
        this.archived_at = archived_at;
    }
}
