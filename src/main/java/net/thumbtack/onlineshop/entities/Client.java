package net.thumbtack.onlineshop.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table
public class Client extends User implements Serializable {
    private String email;
    private String address;
    private String phone;
    private int deposit;
    private List<Item> cart;

    private List<Orders> orders;

    public Client() {

    }

    public Client(String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, int deposit) {
        super(firstName, lastName, patronymic, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = deposit;
        cart = new ArrayList<Item>();
        orders = new ArrayList<Orders>();
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Item> getItems() {
        return cart;
    }

    public void setItems(List<Item> cart) {
        this.cart = cart;
    }

    @Column(nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = true, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(nullable = true, unique = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(nullable = true)
    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void addItem(Product product, Integer count) {
        for (Item item: cart) {
            if (item.getProduct().equals(product)) {
                item.setAmount(item.getAmount() + count);
                return;
            }
        }
        cart.add(new Item(product, 1));
    }

    public void deleteItem(Product product) {
        for (Item item : cart) {
            if (item.getProduct().equals(product)) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                    return;
                } else {
                    cart.remove(item);
                    return;
                }
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (deposit != client.deposit) return false;
        if (!email.equals(client.email)) return false;
        if (!address.equals(client.address)) return false;
        return phone.equals(client.phone);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + deposit;
        return result;
    }

    public Item getItemById(int id){
        Optional<Item> item = cart.stream()
                .filter(product -> product.getProduct().getIdProduct() == id)
                .findFirst();
        if(item.isPresent()){
            return item.get();
        }
        return null;

    }

    public void editItem(Item item, int count){
        item.setAmount(count);
    }

    public void deleteItem(Item item){
        cart.remove(item);
    }
}
