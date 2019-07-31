package net.thumbtack.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.onlineshop.errors.UserServiceError;

import java.util.ArrayList;
import java.util.List;

public class OrdersListDTOWithTotal {
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<OrderDTO> orders;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Double sum;

    List<UserServiceError> errors = new ArrayList<>();

    public void addError(UserServiceError error) {
        this.errors.add(error);
    }

    public List<UserServiceError> getErrors() {
        return errors;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
