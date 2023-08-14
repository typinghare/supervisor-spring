package us.jameschan.supervisor.dto;

import java.beans.JavaBean;
import java.util.List;

@JavaBean
public class TaskDto {
    private Long id;
    private Long userId;
    private Long taskId;
    private Long subjectId;
    private Long categoryId;
    private Integer stage;
    private Integer duration;
    private String createdAt;
    private String updatedAt;
    private String startedAt;
    private String resumedAt;
    private String endedAt;
    private String subjectName;
    private String CategoryName;
    private Integer expectedDuration;
    private List<TaskCommentDto> taskCommentDtoList;

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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getResumedAt() {
        return resumedAt;
    }

    public void setResumedAt(String resumedAt) {
        this.resumedAt = resumedAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public Integer getExpectedDuration() {
        return expectedDuration;
    }

    public void setExpectedDuration(Integer expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public List<TaskCommentDto> getTaskCommentDtoList() {
        return taskCommentDtoList;
    }

    public void setTaskCommentDtoList(List<TaskCommentDto> taskCommentDtoList) {
        this.taskCommentDtoList = taskCommentDtoList;
    }
}
