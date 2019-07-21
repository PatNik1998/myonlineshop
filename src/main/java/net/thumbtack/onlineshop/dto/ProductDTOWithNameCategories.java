package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ProductDTOWithNameCategories {
    private int id;
    private String name;
    private int price;
    private int count;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String[] categories;

    public ProductDTOWithNameCategories() {

    }

    public ProductDTOWithNameCategories(int id, String name, int price, int count, String[] categories) {
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

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
