package com.openshift.springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.openshift.springmvc.model.User;

@Component
public class LoginValidator implements Validator {
    
    @Override
    public void validate(Object obj, Errors errors) {
        final User user = (User) obj;
        if(user.getUsername()==null || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "missing.user.username");
        }
        if(user.getPassword()==null || user.getPassword().isEmpty()) {
            errors.rejectValue("password", "missing.user.password");
        }
        
        // TODO: check not exist
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }
}
