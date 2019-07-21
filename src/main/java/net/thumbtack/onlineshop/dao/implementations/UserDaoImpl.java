package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import net.thumbtack.onlineshop.dao.interfaces.UserDao;
import net.thumbtack.onlineshop.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;


@Service
public class UserDaoImpl implements UserDao {
    public User getUserByLoginAndPassword(String login, String password) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = String.format("from " + net.thumbtack.onlineshop.entities.User.class.getCanonicalName() + " where login = '%s' and password = '%s'", login, password);
        Query SQLQuery = session.createQuery(hql);
        User result = (User) SQLQuery.uniqueResult();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }

    public User getUserById(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = String.format("from " + net.thumbtack.onlineshop.entities.User.class.getCanonicalName() + " where id = " + id);
        Query SQLQuery = session.createQuery(hql);
        User result = (User) SQLQuery.uniqueResult();
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }
}
