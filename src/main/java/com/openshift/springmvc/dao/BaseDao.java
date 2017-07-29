package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.hibernate.SessionFactoryUtil;

@Repository
public abstract class BaseDao<E> {
    
    protected final SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
    
    public void save(final E entity) {
        final Session dbSession = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = dbSession.beginTransaction();
            dbSession.save(entity);
            tx.commit();
        } finally {
            dbSession.close();
        }
    }

    public void update(E entity) {
        final Session dbSession = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = dbSession.beginTransaction();
            dbSession.update(entity);
            tx.commit();
        } finally {
            dbSession.close();
        }
    }

    public void delete(E entity) {
        final Session dbSession = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = dbSession.beginTransaction();
            dbSession.delete(entity);
            tx.commit();
        } finally {
            dbSession.close();
        }
    }
    
    public abstract List<E> getByCriteria(final Map<String, Object> criteriaMap);
    
    public abstract E getById(final int id);
    
    public abstract E loadById(final int id);
}
