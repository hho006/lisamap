package com.openshift.springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.openshift.springmvc.model.UploadItem;

@Component
public class UploadValidator implements Validator {
    
    @Override
    public void validate(Object obj, Errors errors) {
        final UploadItem uploadItem = (UploadItem) obj;
        if(uploadItem.getName() == null || uploadItem.getName().isEmpty()) {
            errors.rejectValue("name", "missing.file.name");
        }
        if(uploadItem.getDescription() == null || uploadItem.getDescription().isEmpty()) {
            errors.rejectValue("description", "missing.file.description");
        }
        for (final MultipartFile eachFile : uploadItem.getFiles()) {
            if(eachFile == null || eachFile.isEmpty()) {
                errors.rejectValue("files", "missing.file.uploaded");
            }
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UploadItem.class.isAssignableFrom(clazz);
    }
}
