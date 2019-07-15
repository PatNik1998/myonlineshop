package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.MySession;
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
        Session session = MySession.initSession();
        Administrator del = (Administrator) session.get(Administrator.class, id);
        session.delete(del);
        MySession.endSession(session);
    }

    public List<Administrator> getAll() {
        Session session = MySession.initSession();
        String hql = String.format("from %s", Administrator.class.getCanonicalName());
        Query SQLQuery = session.createQuery(hql);
        ArrayList<Administrator> result = (ArrayList<Administrator>) SQLQuery.list();
        MySession.endSession(session);
        return result;
    }

    public Administrator getByToken(String token) {
        Session session = MySession.initSession();
        String hql = String.format("from " + Administrator.class.getCanonicalName() +  " a , user u where a.id = u.id AND token = '" + token + "'");
        Query SQLQuery = session.createQuery(hql);
        Administrator result = (Administrator) SQLQuery.uniqueResult();
        MySession.endSession(session);
        return result;
    }


    public Administrator getById(int id) {
        Session session = MySession.initSession();
        Administrator result = (Administrator) session.get(Administrator.class, id);
        MySession.endSession(session);
        return result;
    }

    public void update(Administrator administrator) {
        Session session = MySession.initSession();
        session.update(administrator);
        MySession.endSession(session);
    }

    public void add(Administrator administrator) {
        Session session = MySession.initSession();
        session.save(administrator);
        MySession.endSession(session);
    }

    public void clear() {
        Session session = MySession.initSession();
        String hql = String.format("delete from %s", Administrator.class.getCanonicalName());
        Query query = session.createQuery(hql);
        query.executeUpdate();
        MySession.endSession(session);
    }
}
