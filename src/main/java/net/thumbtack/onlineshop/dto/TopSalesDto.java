package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;

public class TopSalesDto {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ProductDto> products;

    List<UserServiceError> errors = new ArrayList<>();

    public void addError(UserServiceError error) {
        this.errors.add(error);
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }
    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
