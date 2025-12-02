package org.example.dao;


import org.example.config.DbConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class BaseDao {

    protected <T> T save(T entity) {
        Transaction tx = null;
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    protected <T> T find(Class<T> clazz, Long id) {
        try (Session session = DbConfig.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }
}
