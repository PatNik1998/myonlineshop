package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.CategoryDao;
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
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Category del = (Category) session.get(Category.class, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public List<Category> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from %s", Category.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Category> result = (ArrayList<Category>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public Category getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Category result = (Category) session.get(Category.class, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public void update(Category object) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void add(Category object) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }

    public void clear() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s", Category.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}
