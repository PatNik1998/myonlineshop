package net.thumbtack.onlineshop.dao;

import net.thumbtack.onlineshop.common.HibernateSessionFactory;
import org.hibernate.Session;

public class MySession {
    public static Session initSession(){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    public static void endSession(Session session){
        session.getTransaction().commit();
        if (session.isOpen()) {
            session.close();
        }
    }
}
