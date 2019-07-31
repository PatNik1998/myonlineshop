package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.entities.*;
import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        try {
            session.beginTransaction();
            session.update(administrator);
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeEx) {}
            hibernateEx.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void add(Administrator administrator) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(administrator);
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeEx) {}
            hibernateEx.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
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

    private final int ItemsPerPage = 20;

    public List<Orders> getOrdersOfClient(Integer clientId, Integer page) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from Orders where id in (select o.id from Client c inner join c.orders o where c.id = %s )",
                clientId, offset, ItemsPerPage);

        List<Orders> orders = null;
        try{
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setFirstResult(offset);
            SQLQuery.setMaxResults(ItemsPerPage);
            orders = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeEx) {}
            hibernateEx.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return orders;
    }
    public List<Orders> getOrdersOfProduct(Integer productId, Integer page) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from %s where id  in (select o.id from Orders o inner join o.items i where i.id in (" +
                "(select i.id from Item i inner join i.product p where p.id = %s)" +
                "))", Orders.class.getCanonicalName(), productId.toString());

        List<Orders> orders = null;
        try {
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setFirstResult(offset);
            SQLQuery.setMaxResults(ItemsPerPage);
            orders = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
             session.getTransaction().rollback();
            } catch (RuntimeException runtimeEx) {}
            hibernateEx.printStackTrace();
        } finally {
         if (session != null) {
             session.close();
         }
        }

//        for(Orders o : orders){
//            o.setItems(getItemsByOrder(o.getId()));
//        }

        return orders;
    }

    public List<Orders> getOrderList(Integer page){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from %s", Orders.class.getCanonicalName());
        List<Orders> orders = null;
        try {
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setFirstResult(offset);
            SQLQuery.setMaxResults(ItemsPerPage);
            orders = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch (RuntimeException runtimeEx) {
            }
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }

//        for(Orders o : orders){
//            o.setItems(getItemsByOrder(o.getId()));
//        }

        return orders;
    }

    public List<Orders> getTopOrdersByDate(Integer page){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        int offset = ItemsPerPage * page;
        String hql = String.format("from %s where id in " +
                "(select id from Orders o " +
                "GROUP BY YEAR(date), month(date), day(date) ORDER BY count(o) DESC, date DESC)", Orders.class.getCanonicalName());

        List<Orders> prods = null;
        try {
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setFirstResult(offset);
            SQLQuery.setMaxResults(ItemsPerPage);
            prods = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch(RuntimeException runtimeEx){}
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }
        return prods;
    }

    public List<Category> getTopClientCategories(Integer clientId, Integer count){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = String.format("from %s where id in " +
                "(select c.id from Category c inner join c.products p where p.id in " +
                "(select p.id from Item i inner join i.product where i.id in " +
                "(select i1.id from Orders o inner join o.items i1 where o.id in " +
                "(select o1.id from Client cl inner join cl.orders o1 where cl.id = %s))) " +
                "GROUP BY p.id ORDER BY count(p.id) DESC)", Category.class.getCanonicalName(), clientId);

        List<Category> prods = null;
        try {
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setMaxResults(count);
            prods = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch(RuntimeException runtimeEx){}
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }

        return prods;
    }
    public List<Product> getTopSalesByCategory(Integer categoryId, Integer count){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = String.format("from %s where id in (" +
                "select p1.id from Item i inner join i.product p1 inner join p1.categories c where c.id = %s " +
                "GROUP BY p1.id ORDER BY count(p1.id) DESC)", Product.class.getCanonicalName(), categoryId, count);

        List<Product> prods = null;
        try {
            Query SQLQuery = session.createQuery(hql);
            SQLQuery.setFirstResult(count);
            prods = SQLQuery.list();
            session.getTransaction().commit();
        }catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch(RuntimeException runtimeEx){}
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }

        return prods;
    }

    public List<Product> getMonthlyTopSales(Integer count){
        return getTimingTopSales(count, 31);
    }


    public List<Product> getYearlyTopSales(Integer count){
        return getTimingTopSales(count, 365);
    }

    private List<Product> getTimingTopSales(Integer count, Integer daysBefore){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String frmDate = format.format(Date.from(Instant.now().minus(Duration.ofDays(daysBefore))));
        String enDate = format.format(Date.from(Instant.now()));

        String hql = String.format("from %s where id in (" +
                "select p.id from Item i inner join i.product p where i.id in " +
                "(select i1.id from Orders o inner join o.items i1 where " +
                " o.date between '%s' and '%s') " +
                "GROUP BY p.id ORDER BY count(p.id) DESC)", Product.class.getCanonicalName(),frmDate, enDate);
        List<Product> prods = null;
        try{
            Query SQLQuery = session.createQuery(hql);
            prods = SQLQuery.list();
            SQLQuery.setMaxResults(count);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch(RuntimeException runtimeEx){}
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }

        return prods;
    }


    public List<Item> getItemsByOrder(Integer orderId){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        String hql = String.format("from Item where id in (select i.id from Orders o inner join o.items i where o.id = %s )", orderId.toString());
        List<Item> items = null;
        try{
            Query SQLQuery = session.createQuery(hql);
            items = SQLQuery.list();
            session.getTransaction().commit();
        } catch (HibernateException hibernateEx) {
            try {
                session.getTransaction().rollback();
            } catch(RuntimeException runtimeEx){}
            hibernateEx.printStackTrace();
        } finally {
            if(session!= null) {
                session.close();
            }
        }

        return items;
    }
}
