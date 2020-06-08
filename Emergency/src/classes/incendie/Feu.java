package classes.incendie;

import com.google.gson.annotations.SerializedName;
import classes.Fire_type;
import classes.Target;
import classes.ideable;

public class Feu implements ideable,Target {
	
	static int max_id;
	private int id;
	@SerializedName("position_x")
	private double position_x;
	@SerializedName("position_y")
	private double position_y;
	@SerializedName("categorie")
	private Fire_type categorie;
	@SerializedName("intensite")
	private int intensite;
	@SerializedName("detecte")
	private int detecte;
	@SerializedName("prise_en_charge")
	private int prise_en_charge;
	
	public Feu(Fire_type T,int I,double x,double y) {
		this.id=max_id+1;
		this.categorie=T;
		this.intensite=I;
		this.position_x=x;
		this.position_y=y;
		this.detecte=0;
		this.prise_en_charge=0;
	}
	
	
	@Override
	public String toString() {
		String phrase = "je suis le feu "+this.getId()+" de classe " + this.categorie + " et je suis un feu d'intensité " +this.intensite+ " sur 5 ayant pour position[x:"+this.position_x+", y:"+this.position_y+"]";
		return(phrase);
	}
	
	public int getIntensity() {
		return this.intensite;
	}
	
	public Fire_type getType() {
		return this.categorie;
	}

	public double getPosx() {
		return this.position_x;
	}
	
	public double getPosy() {
		return this.position_y;
	}

	public int getId() {
		return this.id;
	}

	public void setPosx(double new_x) {
		this.position_x=new_x;
	}
	
	public void setPosy(double new_y) {
		this.position_y=new_y;
	}
	
	public void setIntensity(int i) {
		if(i<61) {
			this.intensite=i;
			}
	}
	
	public int getprisenecharge() {
		return this.prise_en_charge;
	}
	
	public void setPrisencharge(int pec) {
		this.prise_en_charge=pec;
	}

	
	
}
