package net.thumbtack.onlineshop.dto.response;


import net.thumbtack.onlineshop.dto.request.ProductDTOWithNameCategories;

import java.util.List;

public class BuyProductsDTOResponse {
    private List<ProductDTOWithNameCategories> bought;
    private List<ProductDTOWithNameCategories> remaining;

    public BuyProductsDTOResponse() {

    }

    public BuyProductsDTOResponse(List<ProductDTOWithNameCategories> bought, List<ProductDTOWithNameCategories> remaining) {
        this.bought = bought;
        this.remaining = remaining;
    }

    public List<ProductDTOWithNameCategories> getBought() {
        return bought;
    }

    public void setBought(List<ProductDTOWithNameCategories> bought) {
        this.bought = bought;
    }

    public List<ProductDTOWithNameCategories> getRemaining() {
        return remaining;
    }

    public void setRemaining(List<ProductDTOWithNameCategories> remaining) {
        this.remaining = remaining;
    }
}
