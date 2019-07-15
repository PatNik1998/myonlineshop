package net.thumbtack.onlineshop.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Client extends User implements Serializable {
    @Column(nullable = true, length = 45)
    private String email;
    @Column(nullable = true, length = 100)
    private String address;
    @Column(nullable = true, unique = true)
    private String phone;
    @Column(nullable = true)
    private int deposit;
    private List<Item> cart;
    public Client() {

    }

    public Client(String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, int deposit) {
        super(firstName, lastName, patronymic, login, password);
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.deposit = deposit;
        cart = new ArrayList<Item>();
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Item> getItems() {
        return cart;
    }

    public void setItems(List<Item> cart) {
        this.cart = cart;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public void addItem(Product product) {
        for (Item item: cart) {
            if (item.getProduct().equals(product)) {
                item.setAmount(item.getAmount() + 1);
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
}
