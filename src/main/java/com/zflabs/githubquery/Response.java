package com.zflabs.githubquery;

public class Response {
	private String body;
	private int code;

	public Response(int code, String body) {
		this.body = body;
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public int getCode() {
		return code;
	}
}
