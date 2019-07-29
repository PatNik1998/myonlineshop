package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.entities.Product;

import javax.persistence.*;
import java.io.Serializable;

public class ItemDto implements Serializable {

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private int id;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private ProductDto product;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private int amount;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }
    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
