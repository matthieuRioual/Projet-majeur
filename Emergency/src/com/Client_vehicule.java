package com;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import classes.humain.Personnel;
import classes.transport.Vehicule;


public class Client_vehicule extends Client {
	
	public Client_vehicule() {
		super("http://localhost:5001/rest_api/v1.0/vehicule");
	}
	
	public void change_carburant(int id,int carburant) {
		String requestBody = "{ \"carburant\": \"" + (carburant) + "\"}";
		HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());
		try {
			request = requestFactory.buildPutRequest(new GenericUrl(getUrl()+"/modifier_carburant/"+String.valueOf(id)),byteContent);
			request.execute().parseAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void change_produit(int id,int produit) {
		String requestBody = "{ \"carburant\": \"" + (produit) + "\"}";
		HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());
		try {
			request = requestFactory.buildPutRequest(new GenericUrl(getUrl()+"/remplir_produit/"+String.valueOf(id)),byteContent);
			request.execute().parseAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Vehicule> getvehicules() {
		List<Vehicule> listvehicule=new ArrayList<Vehicule>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Vehicule>>(){}.getType();
			listvehicule = (List<Vehicule>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listvehicule;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vehicule> getvehiculesdisponibles() {
		List<Vehicule> listvehicule=new ArrayList<Vehicule>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()+"/disponible"));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Vehicule>>(){}.getType();
			listvehicule = (List<Vehicule>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listvehicule;
	}
	
	@SuppressWarnings("unchecked")
	public List<Vehicule> getvehiculesnondisponibles() {
		List<Vehicule> listvehicule=new ArrayList<Vehicule>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()+"/non_disponible"));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Vehicule>>(){}.getType();
			listvehicule = (List<Vehicule>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listvehicule;
	}
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getpersonnel() {
		List<Personnel> listpers=new ArrayList<Personnel>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Personnel>>(){}.getType();
			listpers = (List<Personnel>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listpers;
	}
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getPersonnelinside(int id){
		List<Personnel> listin=new ArrayList<Personnel>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()+"/personnel_embarque/"+Integer.toString(id)));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Personnel>>(){}.getType();
			listin = (List<Personnel>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listin;
	}
	
}

