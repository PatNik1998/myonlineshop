package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class TopSalesDto {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ProductDto> products;

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
