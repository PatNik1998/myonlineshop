package net.thumbtack.onlineshop.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer parentId;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String parentName;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<UserServiceError> errors;

    public CategoryDto() {
        errors = new ArrayList<>();
    }

    public CategoryDto(int id, String name, int parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<UserServiceError> errors) {
        this.errors = errors;
    }

    public void addError(UserServiceError error) {
        errors.add(error);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
