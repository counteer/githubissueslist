package com.zflabs.githubquery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubConnection {

	private static final String SERVER_URL = "https://api.github.com/repos/";
	private static final String SERVER_URL_END = "issues?state=open&page=";

	public HttpURLConnection createConnection(String repo, int key, String requestMethod) {
		try {
			URL url = new URL(SERVER_URL + repo+SERVER_URL_END+key);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(requestMethod);
			return con;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public Response getResponse(HttpURLConnection con) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			int status = con.getResponseCode();
			if(status == HttpURLConnection.HTTP_NO_CONTENT) {
				return new Response(status, null);
			}
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine).append(System.lineSeparator());
			}
			return new Response(status, content.toString());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
