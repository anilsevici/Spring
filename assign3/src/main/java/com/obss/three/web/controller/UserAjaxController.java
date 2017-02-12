package com.obss.three.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.obss.three.users.model.JsonRequest;
import com.obss.three.users.model.JsonResponse;
import com.obss.three.users.service.UserService;
import com.obss.three.validator.EmailValidator;
import com.obss.three.web.jsonview.Views;

@RestController
public class UserAjaxController {

	@Autowired
	private UserService userservice;

	@JsonView(Views.Public.class)
	@RequestMapping(value = "/search/api/getSearchResult")
	public JsonResponse getSearchResultViaAjax(@RequestBody JsonRequest req) {

		JsonResponse result = new JsonResponse();
		EmailValidator valid = new EmailValidator();

		if (!userservice.isUserUsernameUnique(req.getUsername())) {
			result.setUsermsg("Username Exist!");
			result.setStatus("FAIL");
		}

		if (!userservice.isUserEmailUnique(req.getEmail())) {
			result.setEmailmsg("Email Exist!");
			result.setStatus("FAIL");
		}

		if (!valid.validate(req.getEmail())) {
			result.setValidemail("Email is not valid eg.anilsevici@gmail.com");
			result.setStatus("FAIL");
		}

		return result;

	}

}
