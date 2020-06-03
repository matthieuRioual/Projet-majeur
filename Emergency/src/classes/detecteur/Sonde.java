package classes.detecteur;
import com.google.gson.annotations.SerializedName;

import classes.ideable;

public class Sonde implements ideable{

	public static int max_id;
	private int id;
	
	@SerializedName("type")
	private Type_detecteur type;
	
	@SerializedName("rate")
	private int rate; 
	
	@SerializedName("position_x")
	private double position_x;
	
	@SerializedName("position_y")
	private double position_y;
	
	@SerializedName("etat")
	private double etat;
	
	@SerializedName("alarme")
	private int alarme;
	
	public Sonde(Type_detecteur type,int rate,double position_x,double position_y,double etat) {
		super();
		this.id=max_id;
		max_id++;
		this.type=type;
		this.rate=rate;
		this.position_x=position_x;
		this.position_y=position_y;
		this.etat=etat;
		this.alarme=0;
	}
	
	@Override
	public int getId() {
		return this.id;
	}

}
