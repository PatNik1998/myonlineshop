package net.thumbtack.onlineshop.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;

public class ProductDTOWithIdCategories {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer price;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer count;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer[] categories;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<UserServiceError> errors;

    public ProductDTOWithIdCategories() {
        errors = new ArrayList<>();
    }

    public ProductDTOWithIdCategories(Integer id, String name, Integer price, Integer count, Integer[] categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.categories = categories;
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

    public Integer[] getCategories() {
        return categories;
    }

    public void setCategories(Integer[] categories) {
        this.categories = categories;
    }
}
