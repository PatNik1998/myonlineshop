package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class OrdersListDTOWithTotal {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<OrderDTO> orders;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private double sum;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
