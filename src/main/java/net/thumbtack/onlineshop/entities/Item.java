package net.thumbtack.onlineshop.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private int amount;

    public Item() {

    }

    public Item(Product product) {
        this.product = product;
    }

    public Item(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (amount != item.amount) return false;
        return product.equals(item.product);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + product.hashCode();
        result = 31 * result + amount;
        return result;
    }
}
