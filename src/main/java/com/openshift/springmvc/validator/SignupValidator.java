package com.openshift.springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.openshift.springmvc.model.User;

@Component
public class SignupValidator implements Validator {
    
    @Override
    public void validate(Object obj, Errors errors) {
        final User user = (User) obj;
        if(user.getUsername()==null || user.getUsername().isEmpty()) {
            errors.rejectValue("username", "missing.user.username");
        }
        if(user.getPassword()==null || user.getPassword().isEmpty()) {
            errors.rejectValue("password", "missing.user.password");
        }
        if(user.getEmail()==null || user.getEmail().isEmpty()) {
            errors.rejectValue("email", "missing.user.email");
        }
        // TODO: check not exist and password strength
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }
}
