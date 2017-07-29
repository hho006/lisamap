package com.openshift.springmvc.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.openshift.springmvc.model.Country;

@Repository
public class CountryDao extends BaseDao<Country> {

    public final List<Country> listAll() {
        final Session dbSession = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        final List<Country> results = dbSession.createQuery("FROM Country").list(); 
        dbSession.close();
        return results;
    }
    
    @Override
    public final List<Country> getByCriteria(Map<String, Object> criteriaMap) {
        final Session dbSession = sessionFactory.openSession();
        final Criteria criteria = dbSession.createCriteria(Country.class);
        for(final Entry<String, Object> entry : criteriaMap.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            criteria.add(Restrictions.eq(key, value));
        }
        @SuppressWarnings("unchecked")
        final List<Country> results = criteria.list();
        dbSession.close();
        return results;
    }
    
    @Override
    public final Country getById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Country result = (Country) dbSession.get(Country.class, id); 
        dbSession.close();
        return result;
    }
    
    @Override
    public final Country loadById(int id) {
        final Session dbSession = sessionFactory.openSession();
        final Country result = (Country) dbSession.load(Country.class, id); 
        dbSession.close();
        return result;
    }
}
