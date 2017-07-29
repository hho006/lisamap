package com.openshift.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.openshift.springmvc.dao.PhotoDao;
import com.openshift.springmvc.model.Photo;

@Service
public class PhotoManager {
    
    private final PhotoDao dao = new PhotoDao();
    
    public void createPhoto(final Photo photo) {
        dao.save(photo);
    }
    
    public void deletePhoto(final Photo photo) {
        dao.delete(photo);
    }
    
    public void updatePhoto(final Photo photo) {
        dao.update(photo);
    }
    
    public final List<Photo> getPhotosByAlbumId(final int albumId) {
        final Map<String, Object> critMap = new HashMap<>();
        critMap.put("albumId", albumId);
        return dao.getByCriteria(critMap);
    }
}
