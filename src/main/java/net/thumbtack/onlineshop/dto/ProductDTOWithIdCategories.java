package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProductDTOWithIdCategories {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer price;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer count;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private int[] categories;

    public ProductDTOWithIdCategories() {

    }

    public ProductDTOWithIdCategories(int id, String name, int price, int count, int[] categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.categories = categories;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }
}
