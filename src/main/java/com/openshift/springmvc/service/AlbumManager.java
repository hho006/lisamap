package com.openshift.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.openshift.springmvc.dao.AlbumDao;
import com.openshift.springmvc.model.Album;

@Service
public class AlbumManager {
    
    private final AlbumDao dao = new AlbumDao();
    
    public void createAlbum(final Album album) {
        dao.save(album);
    }
    
    public void deleteAlbum(final Album album) {
        dao.delete(album);
    }
    
    public void updateAlbum(final Album album) {
        dao.update(album);
    }
    
    public final List<Album> getPhotoAlbumsByUserId(final int userId) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("userid", userId);
        critMap.put("type", Album.AlbumType.PHOTO.getTypeValue());
        return dao.getByCriteria(critMap);
    }
    
    public final List<Album> getVideoAlbumsByUserId(final int userId) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("userid", userId);
        critMap.put("type", Album.AlbumType.VIDEO.getTypeValue());
        return dao.getByCriteria(critMap);
    }
    
    public final List<Album> getPhotoAlbumsByCountryId(final int countryId, final int userId) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("countryId", countryId);
        critMap.put("userid", userId);
        critMap.put("type", Album.AlbumType.PHOTO.getTypeValue());
        return dao.getByCriteria(critMap);
    }
    
    public final List<Album> getVideoAlbumsByCountryId(final int countryId, final int userId) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("countryId", countryId);
        critMap.put("userid", userId);
        critMap.put("type", Album.AlbumType.VIDEO.getTypeValue());
        return dao.getByCriteria(critMap);
    }
    
    public final Album getAlbumById(final int albumId) {
        return dao.getById(albumId);
    }
}
