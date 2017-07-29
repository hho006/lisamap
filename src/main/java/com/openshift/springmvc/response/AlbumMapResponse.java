package com.openshift.springmvc.response;

import java.util.List;

import com.openshift.springmvc.model.Album;

public class AlbumMapResponse {
    
    public int statusCode;
    
    public int userId;
    
    public int countryId;
    
    public List<Album> albumList;
    
    public List<String> coverImageUrlList;
    
    public final int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
    
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
    
    public final List<Album> getAlbumList() {
        return albumList;
    }
    
    public void setAlbumList(final List<Album> albumList) {
        this.albumList = albumList;
    }
    
    public final List<String> getCoverImageUrlList() {
        return coverImageUrlList;
    }
    
    public void setCoverImageUrlList(final List<String> coverImageUrlList) {
        this.coverImageUrlList = coverImageUrlList;
    }
}
