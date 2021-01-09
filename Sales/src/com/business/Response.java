package com.business;

public class Response {
	private String type;
	private String message;

	public String getType() {
		return type;
	}

	public void setTypeMesssage() {
		this.type = "message";
	}

	public void setTypeError() {
		this.type = "error";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
