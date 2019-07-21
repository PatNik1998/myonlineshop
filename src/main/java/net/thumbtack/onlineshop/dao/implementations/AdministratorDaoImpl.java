package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.interfaces.AdministratorDao;
import net.thumbtack.onlineshop.dao.interfaces.CommonDao;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministratorDaoImpl implements AdministratorDao {
    public void delete(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Administrator del = (Administrator) session.get(Administrator.class, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public List<Administrator> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from %s", Administrator.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Administrator> result = (ArrayList<Administrator>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public Administrator getByToken(String token) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from " + Administrator.class.getCanonicalName() +  " a , user u where a.id = u.id AND token = '" + token + "'");
        Query SQLQuery = session.createQuery(hql);
        Administrator result = (Administrator) SQLQuery.uniqueResult();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }


    public Administrator getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Administrator result = (Administrator) session.get(Administrator.class, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public void update(Administrator administrator) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(administrator);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void add(Administrator administrator) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(administrator);
        session.getTransaction().commit();
        session.close();
    }

    public void clear() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s", Administrator.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}
