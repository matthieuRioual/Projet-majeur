package com;

import java.io.IOException;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;

import classes.ideable;


public class Client {
	
	protected String url;
	HttpRequestFactory requestFactory
	  = new NetHttpTransport().createRequestFactory();
	
	HttpRequest request;
	
	public Client(String url) {
		super();
		this.setUrl(url);
	}
	
	public void Ajout(Object o) {
		Gson gson=new Gson();

		String requestBody = gson.toJson(o);
		HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());


		try {

			request = requestFactory.buildPostRequest(new GenericUrl(this.getUrl()+"/ajout"),byteContent);
			String rawResponse = request.execute().parseAsString();
			System.out.println(rawResponse);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Supression(int id) {

		try {
			
			request = requestFactory.buildDeleteRequest(new GenericUrl(getUrl()+"/suppression/"+String.valueOf(id)));
			String rawResponse = request.execute().parseAsString();
			System.out.println(rawResponse);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Misajour(ideable i,String requestBody) {
		try {
			HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());

			request = requestFactory.buildPutRequest(new GenericUrl(getUrl()+"/mise_a_jour/"+String.valueOf((i.getId()))),byteContent);
			request.execute();


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
