package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.entities.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;


@Component
public class Sessions {


    private Map<String, User> sessions = new HashMap<>();

    private Map<Integer,Cookie> tokens = new HashMap<>();

    public void addSession(String token, User user) {
        sessions.put(token, user);
    }

    public void removeSession(String token) {
        sessions.remove(token);
    }

    public User getUser(String token) {
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