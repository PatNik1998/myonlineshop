package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.ProductDao;
import net.thumbtack.onlineshop.entities.Product;
import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDaoImpl implements ProductDao {
    public void delete(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Product del = (Product) session.get(Product.class, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public List<Product> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from %s", Product.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Product> result = (ArrayList<Product>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public Product getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Product result = (Product) session.get(Product.class, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public void update(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void add(Product product) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    public void clear() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s", Product.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}
