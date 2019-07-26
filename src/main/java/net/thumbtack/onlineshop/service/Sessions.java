package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.dao.UserDao;
import net.thumbtack.onlineshop.dao.implementations.UserDaoImpl;
import net.thumbtack.onlineshop.dto.UserDTO;
import net.thumbtack.onlineshop.entities.User;
import net.thumbtack.onlineshop.errors.UserErrorCode;
import net.thumbtack.onlineshop.errors.UserServiceError;
import net.thumbtack.onlineshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;


@Component
public class Sessions {

    private UserDao userDao;

    @Autowired
    public Sessions(UserDaoImpl userDao){
        this.userDao = userDao;
    }

    public User getUser(String sessionId) {
        Integer id = getId(sessionId);
        User user = userDao.getUserById(id);
        return user;
    }
    private Map<String, Integer> sessions = new HashMap<>();

    private Map<Integer,Cookie> tokens = new HashMap<>();

    public void addSession(String token, Integer id) {
        sessions.put(token, id);
    }

    public void removeSession(String token) {
        sessions.remove(token);
    }

    public Integer getId(String token) {
        return sessions.get(token);
    }

    public void addTokens(Integer id, Cookie token){
        tokens.put(id,token);
    }

    public void removeTokens(Integer id){
        tokens.remove(id);
    }
    public Cookie getToken(Integer id){
        return tokens.get(id);
    }
}