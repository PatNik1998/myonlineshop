package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private int id;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<ItemDto> items;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Date date;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private double sum;

    public List<ItemDto> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
