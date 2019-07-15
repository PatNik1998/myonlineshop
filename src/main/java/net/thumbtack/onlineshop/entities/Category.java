package net.thumbtack.onlineshop.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int idCategory;
    @Column(nullable = false, length = 45, unique = true)
    private String name;
    private Category parentCategory;
    private Set<Product> products;

    public Category() {

    }

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    @ManyToMany(cascade =
            {
                    CascadeType.REMOVE
            })
    @JoinTable (name="product_category",
            joinColumns=@JoinColumn (name="category_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCategory(Category category) {
        this.parentCategory = category;
    }

    @ManyToOne
    @JoinColumn
    public Category getParentCategory() {
        return parentCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (idCategory != category.idCategory) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCategory;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
