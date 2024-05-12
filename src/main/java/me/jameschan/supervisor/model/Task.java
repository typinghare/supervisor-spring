package me.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;
import java.sql.Timestamp;

@Entity
@Table(name = "task")
@JavaBean
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "project_id", nullable = false)
    @Comment("The id of project this task associated with.")
    private Long projectId;

    @Column(name = "title", nullable = false)
    @Comment("The tile of this task. Empty string by default.")
    private String title;

    @Column(name = "started_at")
    @Comment("The start time.")
    private Timestamp started_at;

    @Column(name = "completed_at")
    @Comment("The complete time.")
    private Timestamp completed_at;

    @Column(name = "duration")
    @Comment("The duration in seconds.")
    private Integer duration;
}
