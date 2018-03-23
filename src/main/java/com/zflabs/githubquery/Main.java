package com.zflabs.githubquery;

import java.net.HttpURLConnection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Main {
	private static int count = 0;
	public static void main(String[] args) {
		int page = 1;
		String repo = "jersey/jersey/";
		if(args.length==1) {
			repo =args[0];
		}
		while(printPartial(repo, page)) {
			page++;
		};
		System.out.println(count);
	}

	private static boolean printPartial(String repo, int page) {
		GithubConnection ch = new GithubConnection();
		HttpURLConnection connection = ch.createConnection(repo, page, "GET");
		Response response = ch.getResponse(connection);
		String body = response.getBody();
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(body);
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		for(JsonElement jsonElement2: jsonArray) {
			JsonPrimitive descr = jsonElement2.getAsJsonObject().getAsJsonPrimitive("title");
			JsonPrimitive id = jsonElement2.getAsJsonObject().getAsJsonPrimitive("id");
			System.out.println(id  + "  " + descr);
		}
		count+=jsonArray.size();
		return jsonArray.size()>0;
	}
}
