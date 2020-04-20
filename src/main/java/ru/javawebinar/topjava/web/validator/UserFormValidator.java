package ru.javawebinar.topjava.web.validator;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserFormValidator implements Validator {

    private EmailValidator emailValidator = new EmailValidator();

    @Autowired
    private UserService service;

    public UserFormValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserTo user = (UserTo) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name", "NotEmpty.userForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email", "NotEmpty.userForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmpty(errors, "caloriesPerDay", "NotEmpty.userForm.caloriesPerDay", "NotEmpty.userForm.caloriesPerDay");

        if (user.getEmail() == null || !emailValidator.isValid(user.getEmail(), null) || user.getEmail().length() > 100) {
            errors.rejectValue("email", "Pattern.userForm.email", "Pattern.userForm.email");
        } else if (service.hasEmail(user.getEmail())) {
            errors.rejectValue("email", "Occupied.userForm.email", "Occupied.userForm.email");
        }


        if (user.getCaloriesPerDay() == null || user.getCaloriesPerDay() == 0) {
            errors.rejectValue("caloriesPerDay", "NotEmpty.userForm.caloriesPerDay", "NotEmpty.userForm.caloriesPerDay");
        } else if (user.getCaloriesPerDay() < 10 || user.getCaloriesPerDay() > 10000) {
            errors.rejectValue("caloriesPerDay", "Valid.userForm.caloriesPerDay", "Valid.userForm.caloriesPerDay");
        }

        if (user.getName() != null && (user.getName().length() < 2 || user.getName().length() > 100)) {
            errors.rejectValue("name", "Valid.userForm.name", "Valid.userForm.name");
        }

        if (user.getPassword() != null && (user.getPassword().length() < 5 || user.getPassword().length() > 32)) {
            errors.rejectValue("password", "Valid.userForm.password", "Valid.userForm.password");
        }

    }
}
