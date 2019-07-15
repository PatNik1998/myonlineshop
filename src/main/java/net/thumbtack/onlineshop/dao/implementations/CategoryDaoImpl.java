package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.CategoryDao;
import net.thumbtack.onlineshop.dao.MySession;
import net.thumbtack.onlineshop.entities.Category;
import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryDaoImpl implements CategoryDao {
    public void delete(int id) {
        Session session = MySession.initSession();
        Category del = (Category) session.get(Category.class, id);
        session.delete(del);
        MySession.endSession(session);
    }

    public List<Category> getAll() {
        Session session = MySession.initSession();
        String hql = String.format("from %s", Category.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Category> result = (ArrayList<Category>) SQLQuery.list();
        MySession.endSession(session);
        return result;
    }

    public Category getById(int id) {
        Session session = MySession.initSession();
        Category result = (Category) session.get(Category.class, id);
        MySession.endSession(session);
        return result;
    }

    public Category getByName(String name) {
        Session session = MySession.initSession();
        Category result = (Category) session.get(Category.class, name);
        MySession.endSession(session);
        return result;
    }

    public void update(Category object) {
        Session session = MySession.initSession();
        session.update(object);
        MySession.endSession(session);
    }

    public void add(Category object) {
        Session session = MySession.initSession();
        session.save(object);
        MySession.endSession(session);
    }

    public void clear() {
        Session session = MySession.initSession();
        String hql = String.format("delete from %s", Category.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        MySession.endSession(session);
    }
}
