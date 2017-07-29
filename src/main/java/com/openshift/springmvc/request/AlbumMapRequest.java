package com.openshift.springmvc.request;

public class AlbumMapRequest {
    
    public int userId;
    
    public int countryId;
    
    public String countryName;
    
    public final int getUserId() {
        return userId;
    }
    
    public void setUserId(final int userId) {
        this.userId = userId;
    }
    
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
