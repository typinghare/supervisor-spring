package me.jameschan.supervisor.model;

import jakarta.persistence.*;

import java.beans.JavaBean;
import java.sql.Timestamp;

@Entity
@Table(name = "entry")
@JavaBean
public final class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(
        name = "user_id",
        nullable = false
    )
    private Long userId;

    @Column(
        name = "project_id",
        nullable = false
//        comment = "The id of project this task associated with."
    )
    private Long projectId;

    @Column(
        name = "description",
        nullable = false
//        comment = "The description of this task. Empty string by default."
    )
    private String description;

    @Column(
        name = "started_at"
//        comment = "The start time."
    )
    private Timestamp startedAt;

    @Column(
        name = "stopped_at"
//        comment = "The time when this task stopped"
    )
    private Timestamp stoppedAt;

    @Column(
        name = "duration"
//        comment = "The duration in seconds."
    )
    private Integer duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
