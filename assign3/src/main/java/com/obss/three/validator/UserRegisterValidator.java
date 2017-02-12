package com.obss.three.validator;

import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.obss.three.users.model.User;
import com.obss.three.users.service.UserService;

@Component
public class UserRegisterValidator implements Validator {

	@Autowired
	private UserService userservice;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		EmailValidator valid = new EmailValidator();

		ValidationUtils.rejectIfEmpty(errors, "username", "NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "email", "NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "password", "NotEmpty");
		ValidationUtils.rejectIfEmpty(errors, "passwordConfirm", "NotEmpty");

		if (!user.getPasswordConfirm().equals(user.getPassword()))
			errors.rejectValue("passwordConfirm",
					"Diff.userForm.passwordConfirm");

		if (!userservice.isUserUsernameUnique(user.getUsername())) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		if (!userservice.isUserEmailUnique(user.getEmail())) {
			errors.rejectValue("email", "Duplicate.userForm.email");
		}

		if (!valid.validate(user.getEmail())) {
			errors.rejectValue("email", "NotValid.userForm.email");
		}

	}

}
