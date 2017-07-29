package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.model.BlogMessage;

@Repository
public class BlogMessageDao extends BaseDao<BlogMessage> {

    @Override
    public List<BlogMessage> getByCriteria(Map<String, Object> criteriaMap) {
        final Session dbSession = sessionFactory.openSession();
        final Criteria criteria = dbSession.createCriteria(BlogMessage.class);
        for(final Entry<String, Object> entry : criteriaMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            criteria.add(Restrictions.eq(key, value));
        }
        @SuppressWarnings("unchecked")
        final List<BlogMessage> results = criteria.list();
        dbSession.close();
        return results;
    }

    @Override
    public BlogMessage getById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final BlogMessage result = (BlogMessage) dbSession.get(BlogMessage.class, id); 
        dbSession.close();
        return result;
    }
    
    @Override
    public BlogMessage loadById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final BlogMessage result = (BlogMessage) dbSession.load(BlogMessage.class, id); 
        dbSession.close();
        return result;
    }
}
