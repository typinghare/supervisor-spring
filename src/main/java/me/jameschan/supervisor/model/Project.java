package me.jameschan.supervisor.model;

import jakarta.persistence.*;

import java.beans.JavaBean;
import java.sql.Timestamp;

@Entity
@Table(name = "project")
@JavaBean
public final class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(
        name = "user_id",
        nullable = false
//        comment = "The id of the user to which this subject belong."
    )
    private Long userId;

    @Column(
        name = "name",
        nullable = false,
        length = 32
//        comment = "The name of this subject."
    )
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "started_at")
    private Timestamp startedAt;

    @Column(name = "stopped_at")
    private Timestamp stoppedAt;

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getStoppedAt() {
        return stoppedAt;
    }

    public void setStoppedAt(Timestamp stoppedAt) {
        this.stoppedAt = stoppedAt;
    }
}
