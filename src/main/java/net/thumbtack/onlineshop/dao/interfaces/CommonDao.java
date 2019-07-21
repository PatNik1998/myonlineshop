package net.thumbtack.onlineshop.dao.interfaces;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface CommonDao<T> {
    void delete(int id);
    List<T> getAll();
    T getById(int id);
    void update(T object);
    void add(T object);
    void clear();
}
