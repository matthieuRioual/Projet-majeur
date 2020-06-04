package com;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import classes.incendie.Feu;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class Client_feu extends Client{
	
	
	public Client_feu() {
		super("http://localhost:5000/rest_api/v1.0/incendies");
	}


	@SuppressWarnings("unchecked")
	public List<Feu> getincendies() {
		List<Feu> listfeu=new ArrayList<Feu>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(url));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Feu>>(){}.getType();
			listfeu = (List<Feu>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listfeu;


	}

	public void deleteall() {
		try {
			request = requestFactory.buildDeleteRequest(new GenericUrl(url+"/tout_supprimer"));
			String rawResponse=request.execute().parseAsString();
			System.out.println(rawResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public List<Feu> getincendiesdetecte() {
		List<Feu> listfeu=new ArrayList<Feu>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(url+"/get_detectes"));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Feu>>(){}.getType();
			listfeu = (List<Feu>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listfeu;

	}


	public void pris_en_charge(int id_feu,int id_vehicule) {
		
		try {
			String requestBody="{ \"prise_en_charge\": \"" + id_vehicule + "\" }"; 
			HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());
			request = requestFactory.buildPutRequest(new GenericUrl(url+"/prendre_en_charge/"+String.valueOf(id_feu)),byteContent);
			request.execute();


		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
