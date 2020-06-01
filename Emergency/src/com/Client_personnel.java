package com;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import classes.humain.Personnel;

public class Client_personnel extends Client{

	public Client_personnel() {
		super("http://localhost:5000/rest_api/v1.0/personnel");
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getpersonnel() {
		List<Personnel> listpersonnel=new ArrayList<Personnel>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Personnel>>(){}.getType();
			listpersonnel = (List<Personnel>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listpersonnel;
	}
	
	
	
}
