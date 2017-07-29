package com.openshift.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "albums")
public class Album {
    
    public enum AlbumType {
        PHOTO("photo"), VIDEO("video");
        
        private String typeValue;
        
        AlbumType(final String typeValue) {
            this.typeValue = typeValue;
        }
        
        public String getTypeValue() {
            return typeValue;
        }
    }
    
    @Id
    @GeneratedValue
    @Column(name = "albumid", nullable = false)
    private int albumId;
    
    @Column(name = "albumname", nullable = false)
    private String albumName;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "size", nullable = false)
    private int size;
    
    @Column(name = "userid", nullable = false)
    private int userid;
    
    @Column(name = "countryid", nullable = false)
    private int countryId;
    
    public final int getAlbumId() {
        return albumId;
    }
    
    public void setAlbumId(final int albumId) {
        this.albumId = albumId;
    }
    
    public final String getAlbumName() {
        return albumName;
    }
    
    public void setAlbumName(final String albumName) {
        this.albumName = albumName;
    }
    
    public final String getType() {
        return type;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public final int getSize() {
        return size;
    }
    
    public void setSize(final int size) {
        this.size = size;
    }
    
    public final int getUserid() {
        return userid;
    }
    
    public void setUserid(final int userid) {
        this.userid = userid;
    }
    
    public final int getCountryId() {
        return countryId;
    }
    
    public void setCountryId(final int countryId) {
        this.countryId = countryId;
    }
}