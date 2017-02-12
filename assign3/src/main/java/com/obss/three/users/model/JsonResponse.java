package com.obss.three.users.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.obss.three.web.jsonview.Views;

public class JsonResponse {

	@JsonView(Views.Public.class)
	String usermsg;

	@JsonView(Views.Public.class)
	String emailmsg;

	@JsonView(Views.Public.class)
	String validemail;

	@JsonView(Views.Public.class)
	String status = "SUCCESS";

	public String getUsermsg() {
		return usermsg;
	}

	public void setUsermsg(String usermsg) {
		this.usermsg = usermsg;
	}

	public String getEmailmsg() {
		return emailmsg;
	}

	public void setEmailmsg(String emailmsg) {
		this.emailmsg = emailmsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValidemail() {
		return validemail;
	}

	public void setValidemail(String validemail) {
		this.validemail = validemail;
	}

}
