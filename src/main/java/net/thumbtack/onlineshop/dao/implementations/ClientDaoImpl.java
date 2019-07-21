package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.entities.Client;
import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientDaoImpl implements ClientDao {
    public void delete(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Client del = (Client) session.get(Client.class, id);
        session.delete(del);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public List<Client> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("from %s", Client.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Client> result = (ArrayList<Client>) SQLQuery.list();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public Client getById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        Client result = (Client) session.get(Client.class, id);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }



    public void update(Client client) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(client);
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }

    public void add(Client client) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    public void clear() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = String.format("delete from %s", Client.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}
