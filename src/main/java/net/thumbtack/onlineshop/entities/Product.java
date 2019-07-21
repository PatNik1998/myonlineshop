package net.thumbtack.onlineshop.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Product implements Serializable {
    private int idProduct;
    private int price;
    private String name;
    private Integer count;
    private Set<Category> categories;

    public Product() {

    }

    public Product(int idProduct, int price, String name, Integer count, Set<Category> categories) {
        this.idProduct = idProduct;
        this.price = price;
        this.name = name;
        this.count = count;
        this.categories = categories;
    }
    public Product(int idProduct, int price, String name, Integer count) {
        this(idProduct, price, name, count, new HashSet<Category>());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Column(nullable = false, length = 45, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int cost) {
        this.price = cost;
    }

    @Column(nullable = true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer amount) {
        this.count = amount;
    }

    @ManyToMany(fetch = FetchType.EAGER,cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable (name="product_category",
    joinColumns=@JoinColumn (name="product_id"),
    inverseJoinColumns=@JoinColumn(name="category_id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void deleteCategory(Category category) {
        categories.remove(category);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (idProduct != product.idProduct) return false;
        if (price != product.price) return false;
        if (count != null ? !count.equals(product.count) : product.count != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idProduct;
        result = 31 * result + price;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
