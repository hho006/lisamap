package com.openshift.springmvc.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadItem implements Serializable {
    
    private static final long serialVersionUID = -1038031097908043164L;
    
    private String name;
    
    private String description;
    
    private List<MultipartFile> files;
     
    public final String getName() {
        return name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public final String getDescription() {
        return description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
        
    public final List<MultipartFile> getFiles() {
        return files;
    }
    
    public void setFiles(final List<MultipartFile> files) {
        this.files = files;
    }
}