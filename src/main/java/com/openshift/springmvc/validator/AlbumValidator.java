package com.openshift.springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.openshift.springmvc.model.Album;

@Component
public class AlbumValidator implements Validator {
    
    @Override
    public void validate(Object obj, Errors errors) {
        final Album album = (Album) obj;
        if(album.getAlbumName() == null || album.getAlbumName().isEmpty()) {
            errors.rejectValue("albumName", "missing.album.name");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Album.class.isAssignableFrom(clazz);
    }
}
