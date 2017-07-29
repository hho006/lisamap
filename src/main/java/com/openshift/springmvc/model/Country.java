package com.openshift.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class Country {
    
    @Id
    @Column(name = "countryid", nullable = false)
    private int countryId;
    
    @Column(name = "countryname", nullable = false)
    private String countryName;
    
    public final int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(final int countryId) {
        this.countryId = countryId;
    }
    
    public final String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }
}