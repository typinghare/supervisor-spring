package us.jameschan.supervisor.dto;

import java.beans.JavaBean;

@JavaBean
public class TaskCreateDto {
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
