package com;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import classes.depot.Caserne;
import classes.humain.Personnel;
import classes.transport.Vehicule;

public class Client_caserne extends Client{
	
	public Client_caserne() {
		super("http://localhost:5001/rest_api/v1.0/caserne");
	}

	@SuppressWarnings("unchecked")
	public List<Caserne> getcaserne() {
		List<Caserne> listcaserne=new ArrayList<Caserne>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Caserne>>(){}.getType();
			listcaserne = (List<Caserne>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listcaserne;
	}

	@SuppressWarnings("unchecked")
	public List<Vehicule> getvehiculesofcaserne(int id) {
		List<Vehicule> listvehiculeofcaserne=new ArrayList<Vehicule>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()+"/afficher_vehicules/"+String.valueOf(id)));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Vehicule>>(){}.getType();
			listvehiculeofcaserne = (List<Vehicule>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listvehiculeofcaserne;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getpersonnelsofcaserne(int id) {
		List<Personnel> listpersonnelofcaserne=new ArrayList<Personnel>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(getUrl()+"/afficher_personnel/"+String.valueOf(id)));
			String response = request.execute().parseAsString();
			if(response.isEmpty()) {
				break request;
			}				
			Type collectionType = new TypeToken<List<Vehicule>>(){}.getType();
			listpersonnelofcaserne = (List<Personnel>) new Gson()
			               .fromJson( response ,collectionType);
			} catch (IOException e) {
			e.printStackTrace();
		}
	return listpersonnelofcaserne;
	}
	
	

	
}
