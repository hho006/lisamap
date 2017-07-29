package com.openshift.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openshift.springmvc.model.Album;
import com.openshift.springmvc.model.Photo;
import com.openshift.springmvc.request.AlbumMapRequest;
import com.openshift.springmvc.response.AlbumMapResponse;
import com.openshift.springmvc.service.AlbumManager;
import com.openshift.springmvc.service.PhotoManager;

@Controller
public class AjaxController {
    
    private static final int HTTP_200_OK = 200;
    
    private AlbumManager albumManager = new AlbumManager();
    private PhotoManager photoManager = new PhotoManager();
    
    // TODO: should use @RequestParam with GET instead of @RequestBody with POST
    @RequestMapping(value="/ajax/map/album", method=RequestMethod.POST)
    public final @ResponseBody AlbumMapResponse albumOnMap(@RequestBody final AlbumMapRequest request) {
        // get user albums by country id and 1st image as cover
        final int userId = request.getUserId();
        final int countryId = request.getCountryId();
        final List<Album> albumList = albumManager.getPhotoAlbumsByCountryId(countryId, userId);
        final List<String> coverImageUrlList = new ArrayList<>();
        for(final Album album : albumList) {
            final List<Photo> photosInAlbum = photoManager.getPhotosByAlbumId(album.getAlbumId());
            if(!photosInAlbum.isEmpty()) {
                final StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("data:image/jpeg;base64,");
                strBuilder.append(DatatypeConverter.printBase64Binary(photosInAlbum.get(0).getImage()));
                coverImageUrlList.add(strBuilder.toString());
            } else {
                coverImageUrlList.add(""); // TODO: some default cover image
            }
        }
        final AlbumMapResponse response = new AlbumMapResponse();
        response.setStatusCode(HTTP_200_OK);
        response.setUserId(userId);
        response.setCountryId(countryId);
        response.setAlbumList(albumList);
        response.setCoverImageUrlList(coverImageUrlList);
        return response;
    }
    
}