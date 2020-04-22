package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService service;

    public UserFormValidator() {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserTo user = (UserTo) target;
        AuthorizedUser currentUser = ru.javawebinar.topjava.web.SecurityUtil.safeGet();

        if (user.getEmail() != null && service.hasEmail(user.getEmail())) {
            if (currentUser == null || !service.getByEmail(user.getEmail()).getId().equals(currentUser.getId()))
                errors.rejectValue("email", "Occupied.userForm.email", "Occupied.userForm.email");
        }
    }
}
