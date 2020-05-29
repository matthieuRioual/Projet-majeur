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
	
	@SerializedName("error")
	private double erreur;
	
	@SerializedName("detecte")
	private int detecte;
	
	public Sonde(Type_detecteur type,int rate,int position_x,int position_y,double erreur) {
		super();
		this.id=max_id;
		max_id++;
		this.type=type;
		this.rate=rate;
		this.position_x=position_x;
		this.position_y=position_y;
		this.erreur=erreur;
		this.detecte=0;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
