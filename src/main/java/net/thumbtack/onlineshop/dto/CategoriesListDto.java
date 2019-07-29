package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class CategoriesListDto {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<CategoryDto> categories;

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
