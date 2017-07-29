package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.model.Photo;

@Repository
public class PhotoDao extends BaseDao<Photo> {

    @Override
    public final List<Photo> getByCriteria(Map<String, Object> criteriaMap) {
        final Session dbSession = sessionFactory.openSession();
        final Criteria criteria = dbSession.createCriteria(Photo.class);
        for(final Entry<String, Object> entry : criteriaMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            criteria.add(Restrictions.eq(key, value));
        }
        @SuppressWarnings("unchecked")
        final List<Photo> results = criteria.list();
        dbSession.close();
        return results;
    }

    @Override
    public final Photo getById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Photo result = (Photo) dbSession.get(Photo.class, id); 
        dbSession.close();
        return result;
    }
    
    @Override
    public final Photo loadById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Photo result = (Photo) dbSession.load(Photo.class, id); 
        dbSession.close();
        return result;
    }
}
