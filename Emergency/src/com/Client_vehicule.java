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
import classes.incendie.Feu;
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

	public void move(Vehicule vehicule,Feu feu) {
		double pas_x=1*Math.pow(10, -3);
		double pas_y=1*Math.pow(10, -3);
		double position_x_vehicule=vehicule.getPosition_x();
		double position_y_vehicule=vehicule.getPosition_y();

		double position_x_feu=feu.getPosx();
		double position_y_feu=feu.getPosy();
		if((position_x_feu<position_x_vehicule) && (Math.abs(position_x_vehicule-position_x_feu)>pas_y))
			pas_x=-1*Math.pow(10, -3);
		else if((position_x_feu>position_x_vehicule) && (Math.abs(position_x_vehicule-position_x_feu)>pas_y))
			pas_x=1*Math.pow(10, -3);
		else
			pas_x=0;
		if((position_y_feu<position_y_vehicule) && (Math.abs(position_y_vehicule-position_y_feu)>pas_y))
			pas_y=-1*Math.pow(10, -3);
		else if((position_y_feu>position_y_vehicule) && (Math.abs(position_y_vehicule-position_y_feu)>pas_y))
			pas_y=1*Math.pow(10, -3);
		else
			pas_y=0;
		try {
			String requestBody="{ \"position_x\": \"" + (position_x_vehicule+pas_x) + "\", \"position_y\": \"" + (position_y_vehicule+pas_y) + "\", \"carburant\":\"" + (vehicule.getCarburant()-1) + "\",\"disponibilite\":\""+vehicule.getDisponibilite()+"\"}"; 
			HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());
			request = requestFactory.buildPutRequest(new GenericUrl(url+"/mise_a_jour_position/"+String.valueOf(vehicule.getId())),byteContent);
			request.execute();
			}	
			catch (IOException e) {
			e.printStackTrace();
			}
	}

	@SuppressWarnings("unchecked")
	public List<Vehicule> getvehiculebyID(int getprisencharge) {
		List<Vehicule> listvehicule=new ArrayList<Vehicule>();
		request:try {
			request = requestFactory.buildGetRequest(new GenericUrl(url+"/"+String.valueOf(getprisencharge)));
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
	
	public void pris_en_charge(int id_feu,int id_vehicule) {
		try {
			String requestBody="{ \"disponibilite\": \"" + id_feu + "\" }"; 
		HttpContent byteContent = new ByteArrayContent("application/json",requestBody.getBytes());
		request = requestFactory.buildPutRequest(new GenericUrl(url+"/mise_a_jour_position/"+String.valueOf(id_vehicule)),byteContent);
		request.execute();
		}
		catch (IOException e) {
		e.printStackTrace();
		}
		
		
	}

	
}

