package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.MySession;
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
        Session session = MySession.initSession();
        Product del = (Product) session.get(Product.class, id);
        session.delete(del);
        session.getTransaction().commit();
        MySession.endSession(session);
    }

    public List<Product> getAll() {
        Session session = MySession.initSession();
        String hql = String.format("from %s", Product.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Product> result = (ArrayList<Product>) SQLQuery.list();
        session.getTransaction().commit();
        MySession.endSession(session);
        return result;
    }

    public Product getById(int id) {
        Session session = MySession.initSession();
        Product result = (Product) session.get(Product.class, id);
        session.getTransaction().commit();
        MySession.endSession(session);
        return result;
    }

    public void update(Product product) {
        Session session = MySession.initSession();
        session.update(product);
        MySession.endSession(session);
    }

    public void add(Product product) {
        Session session = MySession.initSession();
        session.save(product);
        MySession.endSession(session);
    }

    public void clear() {
        Session session = MySession.initSession();
        String hql = String.format("delete from %s", Product.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        MySession.endSession(session);
    }
}
