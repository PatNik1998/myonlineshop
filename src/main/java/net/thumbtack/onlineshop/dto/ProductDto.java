package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDto {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer price;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer count;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Set<Category> categories;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<UserServiceError> errors;

    public ProductDto() {
        errors = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<UserServiceError> errors) {
        this.errors = errors;
    }
}
