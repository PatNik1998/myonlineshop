package net.thumbtack.onlineshop.dao.implementations;

import net.thumbtack.onlineshop.dao.AdministratorDao;
import net.thumbtack.onlineshop.entities.*;
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

    private final int ItemsPerPage = 20;

    public List<Orders> getOrdersOfClient(Integer clientId, Integer page){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from %s where id  in (select Client_id from client_orders where Client_id = %s ) LIMIT %s,%s",
                Orders.class.getCanonicalName(), clientId, offset,ItemsPerPage);
        Query SQLQuery = session.createQuery(hql);
        List<Orders> orders = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

//        for(Orders o : orders){
//            o.setItems(getItemsByOrder(o.getId()));
//        }

        return orders;
    }
    public List<Orders> getOrdersOfProduct(Integer productId, Integer page){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from %s where id  in (select Orders_Id from orders_item where items_id in (" +
                "(select id from item where product_idProduct = %s)"+
                ") LIMIT %s,%s", Orders.class.getCanonicalName(), productId.toString(), offset, ItemsPerPage);
        Query SQLQuery = session.createQuery(hql);
        List<Orders> orders = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

//        for(Orders o : orders){
//            o.setItems(getItemsByOrder(o.getId()));
//        }

        return orders;
    }

    public List<Orders> getOrderList(Integer page){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        int offset = ItemsPerPage * page;
        String hql = String.format("from %s LIMIT %s,%s", Orders.class.getCanonicalName(), offset, ItemsPerPage);
        Query SQLQuery = session.createQuery(hql);
        List<Orders> orders = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

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
                "(select id from orders, COUNT(*) as count " +
                "GROUP BY YEAR(date), month(date), day(date) ORDER BY count DESC LIMIT %s,%s)", Orders.class.getCanonicalName(), offset, ItemsPerPage);

        Query SQLQuery = session.createQuery(hql);
        List<Orders> prods = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

        return prods;
    }

    public List<Category> getTopClientCategories(Integer clientId, Integer count){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = String.format("from %s where id in " +
                "(select category_id from product_category where product_id in " +
                "(select product_idProduct, COUNT(product_idProduct) as count from item where id in " +
                "(select items_id from orders_item where Orders_id in (select orders_id from client_orders where Client_id = %s)) " +
                "GROUP BY product_idProduct ORDER BY count DESC LIMIT %s))", Category.class.getCanonicalName(), clientId, count);

        Query SQLQuery = session.createQuery(hql);
        List<Category> prods = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

        return prods;
    }
    public List<Product> getTopSalesByCategory(Integer categoryId, Integer count){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = String.format("from %s where id in (" +
                "select product_idProduct, COUNT(product_idProduct) as count from item where product_idProduct in " +
                "(select product_id from product_category where category_id = %s) " +
                "GROUP BY product_idProduct ORDER BY count DESC LIMIT %s)", Product.class.getCanonicalName(), categoryId, count);

        Query SQLQuery = session.createQuery(hql);
        List<Product> prods = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

        return prods;
    }

    public List<Product> getMonthlyTopSales(Integer count){
        return getTimingTopSales(count, "interval 1 month");
    }


    public List<Product> getYearlyTopSales(Integer count){
        return getTimingTopSales(count, "interval 1 year");
    }

    private List<Product> getTimingTopSales(Integer count, String interval){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = String.format("from %s where id in (" +
                "select product_idProduct, COUNT(product_idProduct) as count from item where product_idProduct in " +
                "(select id from orders where id in (select id from orders_item where id = item.id) and date > date_sub(now(), %s))" +
                "GROUP BY product_idProduct ORDER BY count DESC LIMIT %s)", Product.class.getCanonicalName(), interval,count);

        Query SQLQuery = session.createQuery(hql);
        List<Product> prods = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

        return prods;
    }


    public List<Item> getItemsByOrder(Integer orderId){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        String hql = String.format("from %s where id  in (select id from orders_item where Orders_id = %s )", Item.class.getCanonicalName(), orderId.toString());
        Query SQLQuery = session.createQuery(hql);
        List<Item> items = SQLQuery.list();
        session.getTransaction().commit();
        session.close();

        return items;
    }
}
