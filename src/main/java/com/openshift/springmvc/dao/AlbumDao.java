package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.model.Album;

@Repository
public class AlbumDao extends BaseDao<Album> {

    @Override
    public final List<Album> getByCriteria(Map<String, Object> criteriaMap) {
        final Session dbSession = sessionFactory.openSession();
        final Criteria criteria = dbSession.createCriteria(Album.class);
        for(final Entry<String, Object> entry : criteriaMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            criteria.add(Restrictions.eq(key, value));
        }
        @SuppressWarnings("unchecked")
        final List<Album> results = criteria.list();
        dbSession.close();
        return results;
    }
    
    @Override
    public final Album getById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Album result = (Album) dbSession.get(Album.class, id); 
        dbSession.close();
        return result;
    }
    
    @Override
    public final Album loadById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Album result = (Album) dbSession.load(Album.class, id); 
        dbSession.close();
        return result;
    }
}
