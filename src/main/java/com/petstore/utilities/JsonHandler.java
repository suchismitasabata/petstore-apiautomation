package com.petstore.utilities;

import com.google.gson.Gson;

public class JsonHandler {

	public String objectToString(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
}
