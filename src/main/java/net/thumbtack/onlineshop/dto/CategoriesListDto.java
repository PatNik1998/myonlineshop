package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListDto {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<CategoryDto> categories;

    List<UserServiceError> errors = new ArrayList<>();

    public void addError(UserServiceError error) {
        this.errors.add(error);
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
