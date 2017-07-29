package com.openshift.springmvc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "photos")
public class Photo {
    
    @Id
    @GeneratedValue
    @Column(name = "photoid", nullable = false)
    private int photoId;
    
    @Column(name = "albumid", nullable = false)
    private int albumId;
    
    @Column(name = "photoname", nullable = false)
    private String photoName;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "savedtime", nullable = false)
    private Date savedTime;;
    
    @Column(name = "image", nullable = false)
    private byte[] image;
     
    public final int getPhotoId() {
        return photoId;
    }
    
    public void setPhotoId(final int photoId) {
        this.photoId = photoId;
    }
    
    public final int getAlbumId() {
        return albumId;
    }
    
    public void setAlbumId(final int albumId) {
        this.albumId = albumId;
    }
    
    public final String getPhotoName() {
        return photoName;
    }
    
    public void setPhotoName(final String photoName) {
        this.photoName = photoName;
    }
    
    public final String getDescription() {
        return description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public final Date getSavedTime() {
        return savedTime;
    }
    
    public void setSavedTime(final Date savedTime) {
        this.savedTime = savedTime;
    }
    
    public final byte[] getImage() {
        return image;
    }
    
    public void setImage(final byte[] image) {
        this.image = image;
    }
}