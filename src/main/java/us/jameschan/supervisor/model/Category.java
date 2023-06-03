package us.jameschan.supervisor.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.beans.JavaBean;

@Entity
@Table(name = "category")
@JavaBean
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject_id", nullable = false)
    @Comment("The id of the subject to which this category belong.")
    private Long subjectId;

    @Column(name = "user_id", nullable = false)
    @Comment("The id of the user to which this category belong.")
    private Long userId;

    @Column(name = "name", nullable = false, length = 128)
    @Comment("The name of this category.")
    private String name;

    @Column(name = "expected_duration", nullable = false)
    @Comment("The name of this category.")
    private Integer expectedDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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

    public Integer getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(Integer expectedDuration) {
        this.expectedDuration = expectedDuration;
    }
}
