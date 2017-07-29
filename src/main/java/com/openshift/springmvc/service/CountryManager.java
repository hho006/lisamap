package com.openshift.springmvc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openshift.springmvc.dao.CountryDao;
import com.openshift.springmvc.model.Country;

@Service
public class CountryManager {
    
    private final CountryDao dao = new CountryDao();
    
    public void createCountry(final Country country) {
        dao.save(country);
    }
    
    public void deleteCountry(final Country country) {
        dao.delete(country);
    }
    
    public void updateCountry(final Country country) {
        dao.update(country);
    }
    
    public final Country getCountryById(final int countryId) {
        return dao.getById(countryId);
    }
    
    public final List<Country> listAllCountries() {
        // Alternative Usage
        // final Map<String, Object> critMap = new HashMap<>();
        // return dao.getByCriteria(critMap);
        return dao.listAll();
    }
}
