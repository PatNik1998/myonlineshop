package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.dao.ClientDao;
import net.thumbtack.onlineshop.dao.UserDao;
import net.thumbtack.onlineshop.entities.Administrator;
import net.thumbtack.onlineshop.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDaoImpl implements UserDao {

  private AdministratorDao administratorDao;
  private ClientDao clientDao;

  @Autowired
  public UserDaoImpl(AdministratorDaoImpl administratorDao, ClientDaoImpl clientDao){
      this.administratorDao = administratorDao;
      this.clientDao = clientDao;
  }
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

    public User getUserById(Integer id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        String hql = String.format("from " + net.thumbtack.onlineshop.entities.User.class.getCanonicalName() + " where id = " + id);
        Query SQLQuery = session.createQuery(hql);
        User result = (User) SQLQuery.uniqueResult();
        if(result != null){
            Administrator admin = administratorDao.getById(id);
            if(admin == null){
                result = clientDao.getById(id);
            }else{
                result = admin;
            }
        }
        if (session.isOpen()) {
            session.close();
        }
        return result;
    }
}
