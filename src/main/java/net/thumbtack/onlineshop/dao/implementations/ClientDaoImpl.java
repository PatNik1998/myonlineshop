package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.dao.MySession;
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
        Session session = MySession.initSession();
        Client del = (Client) session.get(Client.class, id);
        session.delete(del);
        MySession.endSession(session);
    }

    public List<Client> getAll() {
        Session session = MySession.initSession();
        String hql = String.format("from %s", Client.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Client> result = (ArrayList<Client>) SQLQuery.list();
        session.getTransaction().commit();
        MySession.endSession(session);
        return result;
    }

    public Client getById(int id) {
        Session session = MySession.initSession();
        Client result = (Client) session.get(Client.class, id);
        session.getTransaction().commit();
        MySession.endSession(session);
        return result;
    }

    public void update(Client client) {
        Session session = MySession.initSession();
        session.update(client);
        session.getTransaction().commit();
        MySession.endSession(session);
    }

    public void add(Client client) {
        Session session = MySession.initSession();
        session.save(client);
        MySession.endSession(session);
    }

    public void clear() {
        Session session = MySession.initSession();
        String hql = String.format("delete from %s", Client.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        MySession.endSession(session);
    }
}
