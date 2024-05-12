package me.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;
import java.sql.Timestamp;

@Entity
@Table(name = "timeframe")
@JavaBean
public class Timeframe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "task_id", nullable = false)
    @Comment("The id of the task this timeframe associated with.")
    private Long taskId;

    @Column(name = "started_at")
    @Comment("The start time.")
    private Timestamp started_at;

    @Column(name = "completed_at")
    @Comment("The complete time.")
    private Timestamp completed_at;
}
