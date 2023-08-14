package us.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "user_id", nullable = false)
    @Comment("The id of the user to which this task belong.")
    private Long userId;

    @Column(name = "subject_id", nullable = false)
    @Comment("The id of the subject to which this task belong.")
    private Long subjectId;

    @Column(name = "category_id", nullable = false)
    @Comment("The id of the category to which this task belong.")
    private Long categoryId;

    @Column(name = "stage", nullable = false)
    @Comment("The stage of this task.")
    private Integer stage;

    @Column(name = "duration", nullable = false)
    @Comment("The duration in seconds.")
    private Integer duration;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @Comment("The create time.")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Comment("The update time. It is updated when the task starts.")
    private Timestamp updatedAt;

    @Column(name = "started_at")
    @Comment("The start time.")
    private Timestamp startedAt;

    @Column(name = "resumed_at")
    @Comment("The resume time. It is updated when the task starts.")
    private Timestamp resumedAt;

    @Column(name = "ended_at")
    @Comment("The end time.")
    private Timestamp endedAt;

    @Column(name = "deleted_at")
    @Comment("The delete time.")
    private Timestamp deletedAt;

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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    public Timestamp getResumedAt() {
        return resumedAt;
    }

    public void setResumedAt(Timestamp resumedAt) {
        this.resumedAt = resumedAt;
    }

    public Timestamp getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Timestamp endedAt) {
        this.endedAt = endedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
