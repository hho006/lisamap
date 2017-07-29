package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.model.User;

@Repository
public class UserDao extends BaseDao<User> {

    @Override
    public final List<User> getByCriteria(Map<String, Object> criteriaMap) {
        final Session dbSession = sessionFactory.openSession();
        final Criteria criteria = dbSession.createCriteria(User.class);
        for(final Entry<String, Object> entry : criteriaMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            criteria.add(Restrictions.eq(key, value));
        }
        @SuppressWarnings("unchecked")
        final List<User> results = criteria.list();
        dbSession.close();
        return results;
    }

    @Override
    public final User getById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final User result = (User) dbSession.get(User.class, id); 
        dbSession.close();
        return result;
    }
    
    @Override
    public final User loadById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final User result = (User) dbSession.load(User.class, id); 
        dbSession.close();
        return result;
    }
    
}
