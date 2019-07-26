package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDao {
    User getUserByLoginAndPassword(String login, String password);
    User getUserById(Integer id);
}
