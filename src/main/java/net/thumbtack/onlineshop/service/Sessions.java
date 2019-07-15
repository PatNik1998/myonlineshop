package net.thumbtack.onlineshop.service;

import net.thumbtack.onlineshop.entities.User;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;


@Component
public class Sessions {


    private static Map<String, User> sessions = new HashMap<>();


    public static void addSession(String token, User user) {
        sessions.put(token, user);
    }

    public static void removeSession(String token) {
        sessions.remove(token);
    }

    public static User getUser(String token) {
        return sessions.get(token);
    }
}
