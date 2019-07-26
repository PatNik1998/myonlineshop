package net.thumbtack.onlineshop.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Administrator extends User implements Serializable {

    private String position;

    public Administrator() {
        super();
    }

    public Administrator(String firstName, String lastName, String patronymic, String login, String password, String position) {
        super(firstName, lastName, patronymic, login, password);
        this.position = position;
    }

    @Column(nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator that = (Administrator) o;

        return position.equals(that.position);

    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "position='" + position + '\'' +
                '}';
    }
}
