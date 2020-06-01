package classes.humain;

import com.google.gson.annotations.SerializedName;

import classes.ideable;

public class Personnel implements ideable{

	
	public static int max_id=0;
	private int id;
	
	@SerializedName("categorie_personnel")
	protected Fire_type categorie_personnel;
	
	@SerializedName("vehicule")
	protected int vehicule;
	
	@SerializedName("experience")
	protected double experience;
	
	@SerializedName("caserne")
	protected int caserne;
	
	public Personnel(Fire_type categorie_personnel,double experience,int caserne) {
		super();
		this.id=max_id+1;
		max_id++;
		this.categorie_personnel=categorie_personnel;
		this.experience=experience;
		this.caserne=caserne;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fire_type getCategorie_personnel() {
		return categorie_personnel;
	}

	public void setCategorie_personnel(Fire_type categorie_personnel) {
		this.categorie_personnel = categorie_personnel;
	}

	public int getVehicule() {
		return vehicule;
	}

	public void setVehicule(int vehicule) {
		this.vehicule = vehicule;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public int getCaserne() {
		return caserne;
	}

	public void setCaserne(int caserne) {
		this.caserne = caserne;
	}
	
	
}
